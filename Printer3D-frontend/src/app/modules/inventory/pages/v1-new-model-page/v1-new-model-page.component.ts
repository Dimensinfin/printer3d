// - CORE
import { Component, ViewChild } from '@angular/core';
// - DOMAIN
import { V1AvailablePartsPanelComponent } from '../../panels/v1-available-parts-panel/v1-available-parts-panel.component';

@Component({
    selector: 'v1-new-model-page',
    templateUrl: './v1-new-model-page.component.html',
    styleUrls: ['./v1-new-model-page.component.scss']
})
export class V1NewModelPageComponent {
    @ViewChild(V1AvailablePartsPanelComponent) private availablePartsPanel: V1AvailablePartsPanelComponent;

    // - R E F R E S H A B L E
    public refresh(): void {
        if (null != this.availablePartsPanel) this.availablePartsPanel.refresh();
    }
}
