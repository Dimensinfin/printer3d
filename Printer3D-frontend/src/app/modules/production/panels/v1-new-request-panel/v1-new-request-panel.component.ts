// - CORE
import { Component } from '@angular/core'
// - ROUTER
import { Router } from '@angular/router'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { IsolationService } from '@app/platform/isolation.service'
import { Part } from '@domain/inventory/Part.domain'
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component'
import { RequestItem } from '@domain/production/RequestItem.domain'
import { Model } from '@domain/inventory/Model.domain'
import { DockService } from '@app/modules/innovative/feature-dock/service/dock.service'
import { RequestForm } from '../../domain/RequestForm.domain'
import { ProductionService } from '../../service/production.service'
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto'

@Component({
    selector: 'v1-new-request-panel',
    templateUrl: './v1-new-request-panel.component.html',
    styleUrls: ['./v1-new-request-panel.component.scss']
})
export class V1NewRequestPanelComponent extends BackgroundEnabledComponent {
    public self: V1NewRequestPanelComponent
    public request: RequestForm = new RequestForm()

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService,
        protected productionService: ProductionService,
        protected dockService: DockService) {
        super()
        this.self = this
    }

    // - G E T T E R S
    public getRequestDate(): Date {
        return new Date()
    }
    public getLabel(): string {
        return this.request.label
    }
    public getRequestContents(): RequestItem[] {
        return this.request.getRequestContents()
    }
    public hasContents(): boolean {
        if (this.getRequestContents().length > 0) return true
        else return false
    }
    public getContentCount(): number {
        return this.request.getContentCount()
    }
    public getRequestAmount(): string {
        return this.request.getAmount() + '€'
    }
    public getRequestIva(): string {
        return this.request.getIva() + '€'
    }
    public getRequestTotal(): string {
        return this.request.getTotal() + '€'
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
        this.request.removeContent(content)
    }
    /**
     * Send the new Customer Request to the backend to store on the repository.
     * Discards the returned new Customer Request instance.
     * When the persistence completes the Feature is dismissed and the control goes back to the main dashboard page.
     */
    public saveRequest(): void {
        this.backendConnections.push(
            this.productionService.apiv2_ProductionNewRequest(this.request)
                .subscribe((persistedRequest: CustomerRequestResponse) => {
                    console.log('>[V1NewRequestPanelComponent.saveRequest]> Clear the page')
                    this.dockService.clean() // Clean the selection from any feature
                    this.router.navigate(['/'])
                })
        )
    }
    public cancelRequest(): void {
        this.dockService.clean() // Clean the selection from any feature
        this.router.navigate(['/'])
    }
}
