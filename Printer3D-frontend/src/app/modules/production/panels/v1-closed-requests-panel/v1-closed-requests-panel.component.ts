// - CORE
import { Component, Input } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
import { ProductionService } from '../../service/production.service'
// - DOMAIN
import { V1CommonRequestsPanelComponent } from '../v1-common-requests-panel/v1-common-requests-panel.component'
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { V1ClosedRequestsPageComponent } from '../../pages/v1-closed-requests-page/v1-closed-requests-page.component'

@Component({
    selector: 'v1-closed-requests-panel',
    templateUrl: './v1-closed-requests-panel.component.html',
    styleUrls: ['./v1-closed-requests-panel.component.scss']
})
export class V1ClosedRequestsPanelComponent extends V1CommonRequestsPanelComponent {
    @Input() page: V1ClosedRequestsPageComponent

    constructor(
        protected backendService: BackendService,
        protected productionService: ProductionService
    ) {
        super(backendService)
    }

    // - I V I E W E R
    public fireSelectionChanged(): void {
        this.page.selectRequest(this.selection.getFirstSelected() as CustomerRequest)
    }

    // - O V E R I D E N
    protected downloadRequests(): void {
        this.backendConnections.push(
            this.productionService.apiv3_ProductionGetClosedRequests(this)
                .subscribe((requestList: CustomerRequestResponse[]) => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Requests received: ' + requestList.length)
                    this.completeDowload(requestList) // Notify the completion of the download.
                })
        )
    }
}
