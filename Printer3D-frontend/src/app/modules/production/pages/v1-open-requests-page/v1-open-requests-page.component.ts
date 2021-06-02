// - CORE
import { Component, ViewChild } from '@angular/core';
// - DOMAIN
import { V1OpenRequestsPanelComponent } from '../../panels/v1-open-requests-panel/v1-open-requests-panel.component';
import { V1RequestDetailPanelComponent } from '../../panels/v1-request-detail-panel/v1-request-detail-panel.component';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';

@Component({
    selector: 'v1-open-requests-page',
    templateUrl: './v1-open-requests-page.component.html',
    styleUrls: ['./v1-open-requests-page.component.scss']
})
export class V1OpenRequestsPageComponent implements Refreshable{
    @ViewChild(V1OpenRequestsPanelComponent) private openRequestsPanel: V1OpenRequestsPanelComponent;
    @ViewChild(V1RequestDetailPanelComponent) private selectedRequestPanel: V1RequestDetailPanelComponent;
    public self: V1OpenRequestsPageComponent;

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
        this.openRequestsPanel.refresh();
        this.selectedRequestPanel.cleanSelectedRequest();
    }
}
