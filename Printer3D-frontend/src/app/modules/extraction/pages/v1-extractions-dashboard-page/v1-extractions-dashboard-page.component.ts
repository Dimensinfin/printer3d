// - CORE
import { Component, OnInit } from '@angular/core';
import { Extraction } from '@domain/extraction/Extraction.domain';

@Component({
    selector: 'v1-extractions-dashboard-page',
    templateUrl: './v1-extractions-dashboard-page.component.html',
    styleUrls: ['./v1-extractions-dashboard-page.component.scss']
})
export class V1ExtractionsDashboardPageComponent implements OnInit {
    public extractClosedRequests: Extraction

    public ngOnInit(): void {
        this.extractClosedRequests = new Extraction({
            id: "001-closed-customer-requests",
            label: "Extracción\nPedidos\nCerrados",
            link: "/api/v1/accounting/requests/data"
        })
    }
}
