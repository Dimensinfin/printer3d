// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
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
import { PendingJobListResponse } from '@domain/dto/PendingJobListResponse.dto';
import { Job } from '@domain/Job.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/Part.domain';
import { RequestForm } from '@domain/dto/RequestForm.dto';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';

@Component({
    selector: 'v1-new-request-panel',
    templateUrl: './v1-new-request-panel.component.html',
    styleUrls: ['./v1-new-request-panel.component.scss']
})
export class V1NewRequestPanelComponent {
    public self: V1NewRequestPanelComponent;
    public request: RequestForm = new RequestForm();

    constructor() {
        this.self = this;
    }

    public getRequestDate(): Date {
        return new Date();
    }
    public getLabel(): string {
        return this.request.label;
    }
    public getRequestParts(): Part[] {
        return this.request.partsToServe;
    }
    public onDrop(drop: any) {
        console.log('>[V1NewRequestPanelComponent.onDrop]> Drop: ' + JSON.stringify(drop))
        this.request.addPart(drop.dragData)
        console.log('<>>[V1NewRequestPanelComponent.onDrop]')
    }
    public removePart ( part : Part):void{
        this.request.removePart(part);
    }
}
