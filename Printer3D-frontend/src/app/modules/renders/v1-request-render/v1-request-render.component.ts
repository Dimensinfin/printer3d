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
import { Part } from '@domain/Part.domain';
import { IPartProvider } from '@domain/interfaces/IPartProvider.interface';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'v1-request',
    templateUrl: './v1-request-render.component.html',
    styleUrls: ['./v1-request-render.component.scss']
})
export class V1RequestRenderComponent extends NodeContainerRenderComponent {
    public self: V1RequestRenderComponent

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
        this.self = this
    }

    public getUniqueId(): string {
        const request = this.node as Request
        return request.getId();
    }
    public getRequestDate(): Date {
        const request = this.node as Request
        return request.getRequestDate();
    }
    public getLabel(): string {
        const request = this.node as Request
        return request.getLabel();
    }
    public getContentCount(): string {
        const request = this.node as Request
        return request.getContentCount() + '';
    }
    public getAmount(): string {
        const request = this.node as Request
        return request.getAmount();
    }
    public isOpen(): boolean {
        const request = this.node as Request
        return (request.getState() == RequestState.OPEN)
    }
    public selectRequest(): void {
        console.log('>[V1RequestRenderComponent.selectRequest]> Label: ' + this.getLabel())
        this.container.enterSelected(this.node)
    }
    public isCompleted(): boolean {
        const request = this.node as Request
        return (request.getState() == RequestState.COMPLETED)
    }

    // - I N T E R A C T I O N S
    public getContents(): ICollaboration[] {
        const request = this.node as Request
        return request.getContents()
    }
    /**
     * Completes the request by requesting the backend to process the associated Parts. The number os Parts is subtracted from the stocks and the Request is set to the CLOSED state.
     */
    public completeRequest(): void {
        const request = this.node as Request
        // this.isolationService.errorNotification(
        //     'Cierre de pedidos desactivado temporalmente.',
        //     '/PRODUCCION/PEDIDO/BLOQUEO');
        this.backendConnections.push(
            this.backendService.apiRequestsClose_v1(request.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
                    .setTransformation((entrydata: any): Request => {
                        const targetRequest: Request = new Request(entrydata);
                        this.isolationService.successNotification(
                            'Pedido [' + targetRequest.getLabel() + '] completado correctamente.',
                            '/PRODUCCION/PEDIDO/OK');
                        return targetRequest;
                    }))
                .subscribe((request: Request) => {
                    this.router.navigate(['/']);
                }, (error) => {
                    console.log('-[V1RequestRenderComponent.completeRequest.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse) {
                            if (error.error.status == 404) {
                                this.isolationService.errorNotification('Endpoint [' + error.error.path + '] not found on server.', '404 NOT FOUND')
                            } else {
                                const errorInfo: string = error.error.errorInfo
                                const httpStatus: string = error.error.httpStatus
                                const message: string = this.isolationService.exceptionMessageMap(error.error)
                                this.isolationService.errorNotification(message, errorInfo)
                            }
                        }
                })
        )
    }
}
