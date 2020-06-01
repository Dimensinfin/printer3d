// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { Part } from '@domain/Part.domain';
import { Job } from '@domain/Job.domain';
import { V1BuildCountdownTimerPanelComponent } from '@app/modules/renders/v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';

@Component({
    selector: 'v2-machine-render',
    templateUrl: './v2-machine-render.component.html',
    styleUrls: ['./v2-machine-render.component.scss']
})
export class V2MachineRenderComponent {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public target: Job;

    public onDrop(drop: any) {
        this.target = drop.dragData;
    }
    public startBuild(): void {
        this.sessionTimer.activate(this.target.part.buildTime * 60);
    }
}
