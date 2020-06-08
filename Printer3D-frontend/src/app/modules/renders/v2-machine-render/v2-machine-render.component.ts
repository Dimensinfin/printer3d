// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
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
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';

@Component({
    selector: 'v2-machine-render',
    templateUrl: './v2-machine-render.component.html',
    styleUrls: ['./v2-machine-render.component.scss']
})
export class V2MachineRenderComponent extends BackgroundEnabledComponent implements OnInit {
    @ViewChild(V1BuildCountdownTimerPanelComponent) private sessionTimer: V1BuildCountdownTimerPanelComponent;
    @Input() node: Machine;
    public self: V2MachineRenderComponent;
    public target: Part;
    public buildTime: number = 0;
    public building: boolean = false;

    constructor(protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
    }

    /**
    * When the component is created check of the Machine has already a part associated. If so the load the target field so it will disable the drop and also will start the countdown timer.
    */
    public ngOnInit(): void {
        console.log('>[V2MachineRenderComponent.ngOnInit]')
        this.self = this;
        if (null != this.node) this.loadBuildPart();
    }
    /**
     * Use this to report the child timer that the timer should be started automatically. Because the child can initialize later than the Machine render we should use this to report when the target build part is loaded manually or not.
     */
    public isAutostart(): boolean {
        return this.building;
    }
    /**
     * Calculated the build time to setup on the Timer. If the Job was set by dragging the target then the time is the target 'buildTime'.
     * If the target is filled but because the Machine has a running job then the time is the remaining time.
     */
    public getBuildTime(): number {
        console.log('>[V2MachineRenderComponent.getBuildTime]')
        return this.buildTime;
    }
    public onDrop(drop: any) {
        if (null != drop) {
            this.target = drop.dragData;
            this.buildTime = this.target.buildTime*60;
        }
    }
    public startBuild(): void {
        this.backendConnections.push(
            this.backendService.apiMachinesStartBuild_v1(this.node.id, this.target.id,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.infoNotification(
                            'Construccion de pieza [' + this.target.label + '] comenzada con éxito.',
                            '/COMENZAR CONSTRUCCCION'
                        )
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    this.sessionTimer.activate(this.target.buildTime * 60);
                    this.building = true;
                })
        );
    }
    public onClearClick(): void {
        console.log('>[V2MachineRenderComponent.onClearClick]')
        this.backendConnections.push(
            this.backendService.apiMachinesCancelBuild_v1(this.node.id,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Machine".')
                    .setTransformation((entrydata: any): Machine => {
                        this.isolationService.warningNotification(
                            'Construccion de pieza [' + this.target.label + '] cancelada.',
                            '/CANCELAR CONSTRUCCION'
                        )
                        return new Machine(entrydata);
                    }))
                .subscribe((resultMachine: Machine) => {
                    this.sessionTimer.deactivate();
                    this.node = resultMachine;
                    this.target = null;
                    this.building = false;
                })
        );
    }
    private loadBuildPart(): void {
        console.log('>[V2MachineRenderComponent.ngOnInit]> Running: '+ this.node.isRunning())
        if (this.node.isRunning()) {
            this.target = this.node.buildRecord.part;
            this.building = true;
            this.buildTime = this.node.buildRecord.remainingTime*60;
        }
    }
}
