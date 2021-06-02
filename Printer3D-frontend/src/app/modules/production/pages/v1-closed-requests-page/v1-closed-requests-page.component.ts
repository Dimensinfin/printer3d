// - CORE
import { Component, ViewChild } from '@angular/core';
// - DOMAIN
import { V1RequestDetailPanelComponent } from '../../panels/v1-request-detail-panel/v1-request-detail-panel.component';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { V1ClosedRequestsPanelComponent } from '../../panels/v1-closed-requests-panel/v1-closed-requests-panel.component';

@Component({
    selector: 'v1-closed-requests-page',
    templateUrl: './v1-closed-requests-page.component.html',
    styleUrls: ['./v1-closed-requests-page.component.scss']
})
export class V1ClosedRequestsPageComponent implements Refreshable {
    @ViewChild(V1ClosedRequestsPanelComponent) private closedRequestsPanel: V1ClosedRequestsPanelComponent;
    @ViewChild(V1RequestDetailPanelComponent) private selectedRequestPanel: V1RequestDetailPanelComponent;
    public self: V1ClosedRequestsPageComponent;

    constructor() {
        this.self = this;
    }

    // -  E V E N T S
    public selectRequest(request: CustomerRequest): void {
        this.selectedRequestPanel.selectRequest(request);
    }

    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.closedRequestsPanel.refresh();
        this.selectedRequestPanel.cleanSelectedRequest();
    }
}
