// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { AfterViewInit } from '@angular/core';
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
import { environment } from '@env/environment';

@Component({
    selector: 'v2-machine-render',
    templateUrl: './v2-machine-render.component.html',
    styleUrls: ['./v2-machine-render.component.scss']
})
export class V2MachineRenderComponent implements OnInit {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public self: V2MachineRenderComponent;
    public target: Part;
    public building: boolean = false;
    /**
    * When the component is created check of the Machine has already a part associated. If so the load the target field so it will disable the drop and also will start the countdown timer.
    */
    public ngOnInit(): void {
        console.log('>[V2MachineRenderComponent.ngOnInit]')
        this.self = this;
        this.loadBuildPart();
    }
    public getRemainingTime(): number {
        console.log('>[V2MachineRenderComponent.getRemainingTime]')
        return this.getRemainingTimeConverter(this.node.jobInstallmentDate)
    }
    public onDrop(drop: any) {
        if (null != drop)
            this.target = drop.dragData;
    }
    public startBuild(): void {
        this.sessionTimer.activate(this.target.buildTime * 60);
        this.building = true;
    }
    public onClearClick(): void {
        console.log('>[V2MachineRenderComponent.onClearClick]')
        this.target = null;
        this.building = false;
    }
    private getRemainingTimeConverter(startDate: string): number {
        if (!environment.production) return 23 * 60;
    }
    private loadBuildPart(): void {
        if (null != this.node)
            if (null != this.node.currentPart) {
                this.target = this.node.currentPart;
                this.building = true;
            }
    }
}
