// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { V1RequestableElementsPanelComponent } from '../../panels/v1-requestable-elements-panel/v1-requestable-elements-panel.component';

@Component({
    selector: 'v1-new-request-page',
    templateUrl: './v1-new-request-page.component.html',
    styleUrls: ['./v1-new-request-page.component.scss']
})
export class V1NewRequestPageComponent implements Refreshable {
    @ViewChild(V1RequestableElementsPanelComponent) private sellableElements: V1RequestableElementsPanelComponent;
    // @ViewChild(V1NewModelPanelComponent) private modelEditingPanel: V1NewModelPanelComponent;
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        if (null != this.sellableElements) this.sellableElements.refresh();
    }
}
