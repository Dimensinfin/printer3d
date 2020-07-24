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
import { IsolationService } from '@app/platform/isolation.service';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Part } from '@domain/Part.domain';
import { RequestForm } from '@domain/RequestForm.domain';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { RequestFormToRequestConverter } from '@domain/converter/RequestFormToRequest.converter';
import { Request } from '@domain/Request.domain';
import { Part4Request } from '@domain/Part4Request.domain';
import { RequestItem } from '@domain/RequestItem.domain';
import { Model } from '@domain/inventory/Model.domain';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';

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
    public getContentCount(): number {
        let count: number = 0
        for (const content of this.getRequestContents())
            count += content.getQuantity()
        return count
    }
    public getRequestAmount(): string {
        let amount: number = 0
        for (const content of this.getRequestContents())
            amount += content.getPrice() * content.getQuantity()
        return amount + ' €'
    }
    public isFormValid(formState: any): boolean {
        return (formState && this.hasContents())
    }
    public onDrop(drop: any) {
        console.log('>[V1NewRequestPanelComponent.onDrop]> Drop: ' + JSON.stringify(drop))
        if (drop.dragData instanceof Part) this.request.addContent(drop.dragData)
        if (drop.dragData instanceof Model) this.request.addContent(drop.dragData)
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
                        this.isolationService.successNotification('Pedido [' + this.request.label + '] registrado correctamente.', '/PRODUCCION/NUEVO PEDIDO/OK');
                        return new Request(); // Discard the just persisted request and return an empty instance.
                    }))
                .subscribe((persistedRequest: Request) => {
                    console.log('>[V1NewRequestPanelComponent.saveRequest]> Clear the page')
                    this.router.navigate(['/']);
                }, (error) => {
                    console.log('-[V3MachineRenderComponent.startBuild.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        )
    }
    public cancelRequest(): void {
        this.router.navigate(['/']);
    }
}
