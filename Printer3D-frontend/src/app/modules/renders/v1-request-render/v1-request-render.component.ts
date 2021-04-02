// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
import { OnDestroy } from '@angular/core'
import { Input } from '@angular/core'
import { ViewChild } from '@angular/core'
import { Subscription } from 'rxjs'
import { MatDialog } from '@angular/material/dialog'
import { MatDialogConfig } from '@angular/material/dialog'
// - ROUTER
import { Router } from '@angular/router'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { BackendService } from '@app/services/backend.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { IsolationService } from '@app/platform/isolation.service'
import { RequestState } from '@domain/interfaces/EPack.enumerated'
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface'
import { environment } from '@env/environment'
import { HttpErrorResponse } from '@angular/common/http'
import { DeleteConfirmationDialogComponent } from '@app/modules/production/dialogs/delete-confirmation-dialog/delete-confirmation-dialog.component'
import { DockService } from '@app/services/dock.service'

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
        super()
        this.self = this
    }

    public getNode(): CustomerRequest {
        return this.node as CustomerRequest
    }
    public getUniqueId(): string {
        if (this.node) return this.getNode().getId()
        return '-'
    }
    public getRequestDate(): Date {
        if (this.node) return this.getNode().getRequestDate()
        else return undefined
    }
    public getLabel(): string {
        if (this.node) return this.getNode().getLabel()
        else return '-'
    }
    public getContentCount(): string {
        if (this.node) return this.getNode().getContentCount() + ''
        else return '-'
    }
    /**
     * Obtains the number of euros that is the amount to pay for this Custoemr Request.
     * @returns the totl amount price for this request
     */
    public getAmount(): string {
        if (this.node) return this.getNode().getAmount() + ' €'
        else return '- €'
    }
    /** @deprecated */
    public isOpen(): boolean {
        return this.isCompleted()
    }
    public isCompleted(): boolean {
        if (this.node) return (this.getNode().getState() == RequestState.OPEN)
        else return true
  }
    public isSelected(): boolean {
        if (this.getNode()) return this.getNode().isSelected()
        else return false
    }

    // - I N T E R A C T I O N S
    public getContents(): ICollaboration[] {
        const request = this.node as CustomerRequest
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
        const request = this.node as CustomerRequest
        this.backendConnections.push(
            this.backendService.apiProductionRequestsClose_v2(request.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
                    .setTransformation((entrydata: any): CustomerRequest => {
                        const targetRequest: CustomerRequest = new CustomerRequest(entrydata)
                        this.isolationService.successNotification(
                            'Pedido [' + targetRequest.getLabel() + '] completado correctamente.',
                            '/PRODUCCION/PEDIDO/OK')
                        return targetRequest
                    }))
                .subscribe((request: CustomerRequest) => {
                    console.log('-[V1RequestRenderComponent.completeRequest.subscribe]> Label: ' + request.getLabel())
                    this.dockService.clean() // Clean the selection from any feature
                    this.router.navigate(['/'])
                }, (error) => {
                    console.log('-[V1RequestRenderComponent.completeRequest.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        )
    }
    public deleteRequest(): void {
        const request = this.node as CustomerRequest
        let dialogConfig: MatDialogConfig
        dialogConfig = new MatDialogConfig()
        dialogConfig.disableClose = false
        dialogConfig.id = "delete-request-confirmation-dialog"
        dialogConfig.data = {
            request: request
        }
        console.log('-[V1RequestRenderComponent.deleteRequest]> Open dialog')
        const dialogRef = this.matDialog.open(DeleteConfirmationDialogComponent, dialogConfig)
        dialogRef.afterClosed()
            .subscribe(result => {
                console.log('[V1RequestRenderComponent.deleteRequest]> Close detected')
                if (result == 'DELETED')
                    this.router.navigate(['/production/requestlist'])
            })
    }
}
