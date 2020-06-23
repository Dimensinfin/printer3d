// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
// - ROUTER
import { Router } from '@angular/router';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Request } from '@domain/Request.domain';
import { BackendService } from '@app/services/backend.service';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { Machine } from '@domain/Machine.domain';
import { V1BuildCountdownTimerPanelComponent } from '../v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';
import { Job } from '@domain/Job.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { Part } from '@domain/Part.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';

@Component({
    selector: 'v3-machine',
    templateUrl: './v3-machine-render.component.html',
    styleUrls: ['./v3-machine-render.component.scss']
})
export class V3MachineRenderComponent extends BackgroundEnabledComponent implements OnInit {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public self: V3MachineRenderComponent;
    public target: Job;
    public state: string = 'IDLE'
    private remainingTime: number = 0; // The time to run to complete the job in seconds.

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
        this.self = this
    }

    /**
     * When the component is created check of the Machine has already a part associated. If so the load the target field so it will disable the drop and also will start the countdown timer.
     */
    public ngOnInit(): void {
        console.log('>[V3MachineRenderComponent.ngOnInit]')
        this.self = this;
        if (null != this.node) this.loadBuildPart();
        console.log('<[V3MachineRenderComponent.ngOnInit]')
    }

    public getUniqueId(): string {
        const machine = this.node as Machine
        return machine.getId();
    }
    /**
     * Calculated the build time to setup on the Timer. If the Job was set by dragging the target then the time is the target 'buildTime'.
     * If the target is filled but because the Machine has a running job then the time is the remaining time.
     */
    public getBuildTime(): number {
        console.log('>[V2MachineRenderComponent.getBuildTime]')
        return this.remainingTime;
    }
    public completeTime(): void {
        this.state = 'COMPLETED'
    }
    public getPartLabel(): string {
        return this.target.getPart().label;
    }
    public isRunning(): boolean {
        if (this.state == 'RUNNING') return true;
        else return false
    }

    private loadBuildPart(): void {
        console.log('>[V2MachineRenderComponent.loadBuildPart]> Running: ' + this.node.isRunning())
        if (this.node.isRunning()) {
            this.target = new Job({
                id: uuidv4(),
                part: this.node.buildRecord.part
            })
            this.state = 'RUNNING'
            this.remainingTime = this.node.buildRecord.remainingTime * 60;
        }
    }

    // -  E V E N T S
    /**
    * Use this to report the child timer that the timer should be started automatically. Because the child can initialize later than the Machine render we should use this to report when the target build part is loaded manually or not.
    */
    public isAutostart(): boolean {
        if (this.state == 'RUNNING') return true;
        else return false;
    }

    // - I N T E R A C T I O N S
    public onDrop(drop: any) {
        if (null != drop) {
            const job: Job = new Job(drop.dragData);
            this.target = job;
            job.count = 1 // Reset the counter that is the value that comes from the aggregated list
            this.remainingTime = job.getBuildSeconds();
        }
    }
    public changePartCount(newCount: number): void {
        this.remainingTime = this.target.getBuildSeconds() * this.target.count;
        this.sessionTimer.setTime(this.remainingTime)
    }
    public startBuild(): void {
        console.log('>[V2MachineRenderComponent.startBuild]')
        const part: Part = this.target.getPart()
        // const partId = part.getId()
        const jobRequest:JobRequest= new JobRequest(this.target)
        this.backendConnections.push(
            this.backendService.apiMachinesStartBuild_v1(this.node.getId(), jobRequest,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.successNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] comenzada con éxito.',
                            '/COMENZAR CONSTRUCCCION'
                        )
                        console.log('>[V2MachineRenderComponent.startBuild]> EntryData: ' + entrydata)
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    console.log('>[V2MachineRenderComponent.startBuild.subscription]')
                    this.sessionTimer.activate(this.getBuildTime());
                    this.state = 'RUNNING'
                })
        );
        console.log('<[V2MachineRenderComponent.startBuild]')
    }
    public onClear(): void {
        console.log('>[V3MachineRenderComponent.onClear]')
        this.backendConnections.push(
            this.backendService.apiMachinesCancelBuild_v1(this.node.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine" on onClear.')
                    .setTransformation((entrydata: any): Machine => {
                        console.log('>[V2MachineRenderComponent.onClear.setTransformation]> Part Label: ' + this.getPartLabel())
                        this.isolationService.warningNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] cancelada.',
                            '/CANCELAR CONSTRUCCION'
                        )
                        console.log('-[V3MachineRenderComponent.onClear.setTransformation] 2')
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    console.log('-[V3MachineRenderComponent.onClear]')
                    this.sessionTimer.deactivate();
                    this.node = resultMachine;
                    this.target = undefined;
                    this.state = 'IDLE'
                })
        );
    }
    public completeBuild(): void {
        console.log('>[V3MachineRenderComponent.completeBuild]')
        this.backendConnections.push(
            this.backendService.apiMachinesCompleteBuild_v1(this.node.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.warningNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] completada con exito.',
                            '/COMPLETAR/CONSTRUCCION'
                        )
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    this.sessionTimer.deactivate();
                    this.node = resultMachine;
                    this.target = undefined;
                    this.state = 'IDLE'
                })
        );
    }
}
