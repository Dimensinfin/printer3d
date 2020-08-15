// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogConfig } from '@angular/material/dialog';
// - ROUTER
import { Router } from '@angular/router';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Request } from '@domain/production/Request.domain';
import { BackendService } from '@app/services/backend.service';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { DeleteConfirmationDialogComponent } from '@app/modules/production/dialogs/delete-confirmation-dialog/delete-confirmation-dialog.component';
import { DockService } from '@app/services/dock.service';

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
        protected backendService: BackendService,
        protected matDialog: MatDialog,
        protected dockService: DockService) {
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
    public isCompleted(): boolean {
        const request = this.node as Request
        return (request.getState() == RequestState.COMPLETED)
    }
    public isSelected(): boolean {
        if (null != this.getNode()) return this.getNode().isSelected()
        else return false
    }

    // - I N T E R A C T I O N S
    public getContents(): ICollaboration[] {
        const request = this.node as Request
        return request.getContents()
    }
    public selectRequest(): void {
        console.log('>[V1RequestRenderComponent.selectRequest]> Label: ' + this.getLabel())
        this.select()
    }
    /**
     * Completes the request by requesting the backend to process the associated Parts. The number os Parts is subtracted from the stocks and the Request is set to the CLOSED state.
     */
    public completeRequest(): void {
        const request = this.node as Request
        this.backendConnections.push(
            this.backendService.apiProductionRequestsClose_v2(request.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
                    .setTransformation((entrydata: any): Request => {
                        const targetRequest: Request = new Request(entrydata);
                        this.isolationService.successNotification(
                            'Pedido [' + targetRequest.getLabel() + '] completado correctamente.',
                            '/PRODUCCION/PEDIDO/OK');
                        return targetRequest;
                    }))
                .subscribe((request: Request) => {
                    console.log('-[V1RequestRenderComponent.completeRequest.subscribe]> Label: ' + request.getLabel())
                    this.dockService.clean() // Clean the selection from any feature
                    this.router.navigate(['/']);
                }, (error) => {
                    console.log('-[V1RequestRenderComponent.completeRequest.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        )
    }
    public deleteRequest(): void {
        const request = this.node as Request
        let dialogConfig: MatDialogConfig;
        dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = false;
        dialogConfig.id = "delete-request-confirmation-dialog";
        dialogConfig.data = {
            request: request
        }
        console.log('-[V1RequestRenderComponent.deleteRequest]> Open dialog')
        const dialogRef = this.matDialog.open(DeleteConfirmationDialogComponent, dialogConfig);
        dialogRef.afterClosed()
            .subscribe(result => {
                console.log('[V1RequestRenderComponent.deleteRequest]> Close detected');
                if (result == 'DELETED')
                    this.router.navigate(['/production/requestlist']);
            });
    }
}
