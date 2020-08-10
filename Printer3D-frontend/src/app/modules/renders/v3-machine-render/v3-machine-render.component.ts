// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { AfterViewInit } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { platformconstants } from '@app/platform/platform-constants';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { Feature } from '@domain/Feature.domain';
import { V1BuildCountdownTimerPanelComponent } from '../v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';
import { Job } from '@domain/Job.domain';
import { Machine } from '@domain/Machine.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { JobToJobRequestConverter } from '@domain/converter/JobToJobRequest.converter';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'v3-machine',
    templateUrl: './v3-machine-render.component.html',
    styleUrls: ['./v3-machine-render.component.scss']
})
export class V3MachineRenderComponent extends NodeContainerRenderComponent implements AfterViewInit {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private buildTimeTimer: V1BuildCountdownTimerPanelComponent;
    public self: V3MachineRenderComponent;
    public target: Job;
    public state: string = 'IDLE'
    private stateSub: Observable<boolean> = Observable.create((observer) => {
        observer.next(false)
    });
    private remainingTime: number = 0; // The time to run to complete the job in seconds.

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
        this.self = this
    }

    /**
     * This lifecycle event is called when all the view and children are completed so now we found that the timer is available.
     * Start setting the timer to the default state and then load the Job if exists and then check if still running.
     */
    public ngAfterViewInit(): void {
        console.log('>[V3MachineRenderComponent.ngAfterViewInit]> Timer reference: ' + this.buildTimeTimer)
        this.setTimerToDefault()
        setTimeout(() => { // BUGFIX: https://github.com/angular/angular/issues/6005#issuecomment-165911194
            if (this.loadBuildPart() == 'RUNNING') {
                this.showTimer(this.remainingTime)
                this.activateTimer()
            } else
                this.completeTime()
        }, 100)
        console.log('<[V3MachineRenderComponent.ngAfterViewInit]')
    }

    public getNode(): Machine {
        return this.node as Machine
    }
    public getUniqueId(): string {
        const machine = this.node as any
        return machine.getId();
    }
    public getLabel(): string {
        return this.getNode().label
    }
    public getModel(): string {
        return this.getNode().model
    }
    public getCharacteristics(): string {
        return this.getNode().characteristics
    }
    public getPartLabel(): string {
        return this.target.getPart().label;
    }
    public isRunning(): boolean {
        return (this.state == 'RUNNING')
    }
    public isCompleted(): boolean {
        return (this.state == 'COMPLETED')
    }

    private loadBuildPart(): string {
        if (null != this.node) {
            console.log('>[V3MachineRenderComponent.loadBuildPart]> Running: ' + this.getNode().isRunning())
            if (this.getNode().isRunning()) {
                setTimeout(() => {
                    this.target = new Job({
                        id: uuidv4(),
                        part: this.getNode().buildRecord.part,
                        priority: 3,
                        partCount: this.getNode().buildRecord.partCopies
                    })
                }, 100);
                this.remainingTime = this.getNode().buildRecord.remainingTime;
                return 'RUNNING'
            } else return 'IDLE'
        }
    }

    // - I N T E R A C T I O N S
    public completeTime(): void {
        console.log('>[V3MachineRenderComponent.completeTime]')
        this.state = 'COMPLETED'
        console.log('<[V3MachineRenderComponent.completeTime]')
    }
    public onDrop(drop: any) {
        if (null != drop) {
            const job: Job = new Job(drop.dragData);
            this.target = job;
            this.state = 'IDLE'
            this.remainingTime = job.getBuildSeconds();
            this.showTimer(this.remainingTime)
        }
    }
    /**
     * After the user changes the number of copies if desires to start the job it has to exit the area of the Job. At this moment we fire an event and pass the number of coppies from the Job to the Machine.
     * If the number of copies is 'null' then this means the copies field is invalid and that the start job button should be disabled. This can be controlled by the state of the machine. The new state INVALID will disable the buttons.
     * @param newCount the new number of copies set for this job
     */
    public changePartCount(): void {
        this.state = 'IDLE'
        if (null == this.target.getCopies()) this.state = 'INVALID'
        this.remainingTime = this.target.getBuildSeconds() * this.target.getCopies();
        this.showTimer(this.remainingTime)
    }
    public startBuild(): void {
        console.log('>[V3MachineRenderComponent.startBuild]')
        const jobRequest: JobRequest = new JobToJobRequestConverter().convert(this.target)
        this.backendConnections.push(
            this.backendService.apiMachinesStartBuild_v2(this.getNode().getId(), jobRequest,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.successNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] comenzada con éxito.',
                            '/COMENZAR CONSTRUCCCION'
                        )
                        console.log('>[V3MachineRenderComponent.startBuild]> EntryData: ' + entrydata)
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    console.log('>[V3MachineRenderComponent.startBuild.subscription]')
                    this.showTimer(this.remainingTime)
                    this.activateTimer()
                    this.state = 'RUNNING'
                }, (error) => {
                    console.log('-[V3MachineRenderComponent.startBuild.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        );
        console.log('<[V3MachineRenderComponent.startBuild]')
    }
    public onClear(): void {
        console.log('>[V3MachineRenderComponent.onClear]')
        this.backendConnections.push(
            this.backendService.apiMachinesCancelBuild_v1(this.getNode().getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine" on onClear.')
                    .setTransformation((entrydata: any): Machine => {
                        console.log('>[V3MachineRenderComponent.onClear.setTransformation]> Part Label: ' + this.getPartLabel())
                        this.isolationService.warningNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] cancelada.',
                            '/CANCELAR CONSTRUCCION'
                        )
                        console.log('-[V3MachineRenderComponent.onClear.setTransformation] 2')
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    console.log('-[V3MachineRenderComponent.onClear]')
                    this.setTimerToDefault()
                    this.node = resultMachine;
                    this.target = undefined;
                    this.state = 'IDLE'
                }, (error) => {
                    console.log('-[V3MachineRenderComponent.onClear.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        );
    }
    public completeBuild(): void {
        console.log('>[V3MachineRenderComponent.completeBuild]')
        this.backendConnections.push(
            this.backendService.apiMachinesCompleteBuild_v1(this.getNode().getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.successNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] completada con exito.',
                            '/COMPLETAR/CONSTRUCCION'
                        )
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    this.setTimerToDefault()
                    this.node = resultMachine;
                    this.target = undefined;
                    this.state = 'IDLE'
                }, (error) => {
                    console.log('-[V3MachineRenderComponent.completeBuild.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        );
    }
    private setTimerToDefault(): void {
        this.buildTimeTimer.deactivate()
        this.buildTimeTimer.show = false;
        this.buildTimeTimer.hours = 0;
        this.buildTimeTimer.minutes = 0;
    }
    private showTimer(timeToRun: number): void {
        this.buildTimeTimer.setTime(timeToRun)
        this.buildTimeTimer.show = true
    }
    private activateTimer(): void {
        console.log('>[V3MachineRenderComponent.activateTimer]')
        this.buildTimeTimer.activate()
        this.state = 'RUNNING'
        console.log('<[V3MachineRenderComponent.activateTimer]')
    }
}
