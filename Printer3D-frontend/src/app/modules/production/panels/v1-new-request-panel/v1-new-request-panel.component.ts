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
import { RequestForm } from '@domain/RequestForm.domain';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { RequestFormToRequestConverter } from '@domain/converter/RequestFormToRequest.converter';
import { Request } from '@domain/Request.domain';
import { Part4Request } from '@domain/Part4Request.domain';
import { RequestItem } from '@domain/RequestItem.domain';

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
    public getRequestContents(): RequestItem[] {
        return this.request.getRequestContents();
    }
    public hasContents(): boolean {
        if (this.request.contents.length > 0) return true;
        else return false
    }
    public isFormValid(formState: any): boolean {
        return (formState && this.hasContents())
    }
    public onDrop(drop: any) {
        console.log('>[V1NewRequestPanelComponent.onDrop]> Drop: ' + JSON.stringify(drop))
        this.request.addContent(drop.dragData)
        console.log('<>>[V1NewRequestPanelComponent.onDrop]')
    }
    public removeContent(content: RequestItem): void {
        this.request.removeContent(content);
    }
    public saveRequest(): void {
        this.backendConnections.push(
            this.backendService.apiNewRequest_v2(new RequestFormToRequestConverter().convert(this.request), 
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request" dto instance from response.')
                .setTransformation((entrydata: any): Request => {
                    // const persistedRequest: Request = new Request();
                    this.isolationService.successNotification('Pedido [' + this.request.label + '] registrado correctamente.', '/PRODUCCION/NUEVO PEDIDO/OK');
                    return new Request(); // Discard the just persisted request and return an empty instance.
                }))
                .subscribe((persistedRequest: Request) => {
                    console.log('>[V1NewRequestPanelComponent.saveRequest]> Clear the page')
                    this.router.navigate(['/']);
                })
        )
    }
    public cancelRequest(): void {
        this.router.navigate(['/']);
    }
}
