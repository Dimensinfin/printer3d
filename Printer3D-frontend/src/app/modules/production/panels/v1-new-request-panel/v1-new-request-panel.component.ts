// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - ROUTER
import { Router } from '@angular/router';
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
import { Part } from '@domain/Part.domain';
import { RequestForm } from '@domain/dto/RequestForm.dto';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';

@Component({
    selector: 'v1-new-request-panel',
    templateUrl: './v1-new-request-panel.component.html',
    styleUrls: ['./v1-new-request-panel.component.scss']
})
export class V1NewRequestPanelComponent extends BackgroundEnabledComponent {
    public self: V1NewRequestPanelComponent;
    public request: RequestForm = new RequestForm();

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
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
    public hasParts(): boolean {
        if (this.request.partsToServe.length > 0) return true;
        else return false
    }
    public isFormValid(formState: any): boolean {
        return (formState && this.hasParts())
    }
    public onDrop(drop: any) {
        console.log('>[V1NewRequestPanelComponent.onDrop]> Drop: ' + JSON.stringify(drop))
        this.request.addPart(drop.dragData)
        console.log('<>>[V1NewRequestPanelComponent.onDrop]')
    }
    public removePart(part: Part): void {
        this.request.removePart(part);
    }
    public saveRequest(): void {
        this.backendConnections.push(
            this.backendService.apiNewRequest_v1(this.request, new ResponseTransformer().setDescription('Do HTTP transformation to "NewRequest" response.')
                .setTransformation((entrydata: any): RequestForm => {
                    const persistedRequest: RequestForm = new RequestForm();
                    this.isolationService.successNotification('Pedido [' + this.request.label + '] registrado correctamente.', '/PRODUCCION/NUEVO PEDIDO/OK');
                    return persistedRequest;
                }))
                .subscribe((persistedRequest: RequestForm) => {
                    console.log('>[V1NewRequestPanelComponent.saveRequest]> Clear the page')
                    this.router.navigate(['/']);
                })
        )
    }
    public cancelRequest(): void {
        this.router.navigate(['/']);
    }
}
