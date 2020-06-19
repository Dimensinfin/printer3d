// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
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

@Component({
    selector: 'v3-machine',
    templateUrl: './v3-machine-render.component.html',
    styleUrls: ['./v3-machine-render.component.scss']
})
export class V3MachineRenderComponent extends BackgroundEnabledComponent implements OnInit {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public self: V3MachineRenderComponent;
    public state: string = 'IDLE'
    public target: Job;
    public buildTime: number = 0;

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
    }

    /**
     * When the component is created check of the Machine has already a part associated. If so the load the target field so it will disable the drop and also will start the countdown timer.
     */
    public ngOnInit(): void {
        console.log('>[V3MachineRenderComponent.ngOnInit]')
        this.self = this;
        // if (null != this.node) this.loadBuildPart();
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
        return this.buildTime;
    }
    public getPartLabel(): string {
        return this.target.getPart().label;
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
            const job: Job = drop.dragData as Job;
            this.buildTime = job.getBuildSeconds()
            this.target = job;
        }
    }
    public startBuild(): void {
        console.log('>[V2MachineRenderComponent.startBuild]')
        this.backendConnections.push(
            this.backendService.apiMachinesStartBuild_v1(this.node.getId(), this.target.id,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.infoNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] comenzada con éxito.',
                            '/COMENZAR CONSTRUCCCION'
                        )
                        console.log('>[V2MachineRenderComponent.startBuild]> EntryData: ' + entrydata)
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    console.log('>[V2MachineRenderComponent.startBuild.subscription]')
                    this.sessionTimer.activate(this.target.getBuildSeconds());
                    this.state = 'RUNNING'
                })
        );
        console.log('<[V2MachineRenderComponent.startBuild]')
    }
    public onClear(): void {
        console.log('>[V3MachineRenderComponent.onClearClick]')
        this.backendConnections.push(
            this.backendService.apiMachinesCancelBuild_v1(this.node.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.warningNotification(
                            'Construccion de pieza [' + this.getPartLabel() + '] cancelada.',
                            '/CANCELAR CONSTRUCCION'
                        )
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    this.sessionTimer.deactivate();
                    this.node = resultMachine;
                    this.target = null;
                    this.state = 'IDLE'
                })
        );
    }
}
