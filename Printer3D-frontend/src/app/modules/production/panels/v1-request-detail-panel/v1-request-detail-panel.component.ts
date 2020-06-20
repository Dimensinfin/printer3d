// - CORE
import { Component } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { Request } from '@domain/Request.domain';

@Component({
    selector: 'v1-request-detail-panel',
    templateUrl: './v1-request-detail-panel.component.html',
    styleUrls: ['./v1-request-detail-panel.component.scss']
})
export class V1RequestDetailPanelComponent {
    public selectedRequest: Request;
    public container : V1RequestDetailPanelComponent;

    public cleanSelectedRequest(): void {
        this.selectedRequest = undefined;
    }
    public selectRequest(request: Request): void {
        this.selectedRequest = request;
    }
}
