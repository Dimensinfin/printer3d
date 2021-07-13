// - CORE
import { Component, Input } from '@angular/core'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'
// - DOMAIN
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'

@Component({
    selector: 'v1-request-detail-panel',
    templateUrl: './v1-request-detail-panel.component.html',
    styleUrls: ['./v1-request-detail-panel.component.scss']
})
export class V1RequestDetailPanelComponent {
    @Input() variant: string = Printer3DConstants.DEFAULT_VARIANT
    public selectedRequest: CustomerRequest
    public container: V1RequestDetailPanelComponent

    public constructor() {
        this.container = this
    }

    public cleanSelectedRequest(): void {
        this.selectedRequest = undefined
    }
    public selectRequest(request: CustomerRequest): void {
        this.selectedRequest = request
    }
    public getVariant(): string {
        return this.variant
    }
}
