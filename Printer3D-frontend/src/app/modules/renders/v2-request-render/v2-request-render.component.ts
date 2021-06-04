// - CORE
import { Component } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { MatDialogConfig } from '@angular/material/dialog'
// - ROUTER
import { Router } from '@angular/router'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { BackendService } from '@app/services/backend.service'
import { IsolationService } from '@app/platform/isolation.service'
import { RequestState } from '@domain/interfaces/EPack.enumerated'
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface'
import { DeleteConfirmationDialogComponent } from '@app/modules/production/dialogs/delete-confirmation-dialog/delete-confirmation-dialog.component'
import { DockService } from '@app/modules/innovative/feature-dock/service/dock.service'
import { CustomerRequestResponse } from '@app/modules/production/domain/dto/CustomerRequestResponse.dto'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'
import { PayConfirmationDialogComponent } from '@app/modules/production/dialogs/pay-confirmation-dialog/pay-confirmation-dialog.component'

@Component({
    selector: 'v2-request',
    templateUrl: './v2-request-render.component.html',
    styleUrls: ['./v2-request-render.component.scss']
})
export class V2RequestRenderComponent extends NodeContainerRenderComponent {
    public self: V2RequestRenderComponent

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService,
        protected matDialog: MatDialog,
        protected dockService: DockService) {
        super()
        this.self = this
    }

    // - G E T T E R S
    public getNode(): CustomerRequestResponse {
        return this.node as CustomerRequestResponse
    }
    public getUniqueId(): string {
        if (this.node) return this.getNode().getId()
        return '-'
    }
    public getRequestDate(): Date {
        if (this.node) return this.getNode().getRequestDate()
        else return undefined
    }
    public getCustomer(): string {
        if (this.node) return this.getNode().getCustomer()
        else return '-'
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
        if (this.node) return this.getNode().getAmount().toFixed(2) + ' €'
        else return '- €'
    }
    public getIva(): string {
        if (this.node) return (this.getNode().getAmount() * Printer3DConstants.IVA_TAX).toFixed(2) + ' €'
        else return '- €'
    }
    public getTotal(): string {
        if (this.node) return this.getNode().getTotal().toFixed(2) + ' €'
        else return '- €'
    }
    public isCompleted(): boolean {
        if (this.node) return !(this.getNode().getState() == RequestState.OPEN)
        else return false
    }
    public isSelected(): boolean {
        if (this.getNode()) return this.getNode().isSelected()
        else return false
    } public isPaid(): boolean {
        if (this.node) return this.getNode().isPaid()
        else return false
    }
    public getContents(): ICollaboration[] {
        if (this.node) return this.getNode().getContents()
        else return []
    }

    // - I N T E R A C T I O N S
    public selectRequest(): void {
        if (this.getNode()) {
            this.select()
            console.log('>[V1RequestRenderComponent.selectRequest]> Label: ' + this.getLabel())
        }
    }
    public editRequest(): void { }
    /**
     * Completes the request by requesting the backend to process the associated Parts. The number os Parts is subtracted from the stocks and the Request is set to the CLOSED state.
     */
    public deliverRequest(): void {
        const request = this.node as CustomerRequest
        this.backendConnections.push(
            this.backendService.apiProductionRequestsClose_v2(request.getId())
                .subscribe((request: CustomerRequest) => {
                    if (request) console.log('-[V1RequestRenderComponent.completeRequest.subscribe]> Label: ' + request.getLabel())
                    this.isolationService.successNotification(
                        'Pedido [' + request.getLabel() + '] completado correctamente.',
                        '/PRODUCCION/PEDIDO/OK')
                    this.dockService.clean() // Clean the selection from any feature
                    this.router.navigate(['/'])
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
    public payRequest(): void {
        const request = this.node as CustomerRequest
        let dialogConfig: MatDialogConfig
        dialogConfig = new MatDialogConfig()
        dialogConfig.disableClose = false
        dialogConfig.id = "pay-request-confirmation-dialog"
        dialogConfig.data = {
            request: request
        }
        console.log('-[V1RequestRenderComponent.payRequest]> Open dialog')
        const dialogRef = this.matDialog.open(PayConfirmationDialogComponent, dialogConfig)
        dialogRef.afterClosed()
            .subscribe(result => {
                console.log('[V1RequestRenderComponent.payRequest]> Close detected')
                if (result == 'PAID') {
                    this.router.navigate(['/production/requestlist/closed'])
                }
            })
    }
}
