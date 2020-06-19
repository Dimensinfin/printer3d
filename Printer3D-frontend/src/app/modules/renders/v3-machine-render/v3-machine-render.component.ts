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

@Component({
    selector: 'v3-machine',
    templateUrl: './v3-machine-render.component.html',
    styleUrls: ['./v3-machine-render.component.scss']
})
export class V3MachineRenderComponent {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public self: V3MachineRenderComponent;
    public state: string = 'IDLE'
    public target: Job;
    public buildTime: number = 0;

    /**
   * When the component is created check of the Machine has already a part associated. If so the load the target field so it will disable the drop and also will start the countdown timer.
   */
    public ngOnInit(): void {
        console.log('>[V2MachineRenderComponent.ngOnInit]')
        this.self = this;
        // if (null != this.node) this.loadBuildPart();
        console.log('<[V2MachineRenderComponent.ngOnInit]')
    }

    public onDrop(drop: any) {
        if (null != drop) {
            // if (drop.dragData instanceof Job) {
            const job: Job = drop.dragData as Job;
            this.buildTime = job.getBuildSeconds()
            this.target = job;
            // }
        }
    }
    public getUniqueId(): string {
        // this.requestInstance.nativeElement.setAttribute('cy-id', this.identifier);
        const machine = this.node as Machine
        return machine.getId();
    }
    public getBuildTime(): number {
        console.log('>[V2MachineRenderComponent.getBuildTime]')
        return 30;
    }

}
