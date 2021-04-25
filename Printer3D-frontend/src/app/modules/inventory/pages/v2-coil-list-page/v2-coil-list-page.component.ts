// - CORE
import { Component, ViewChild } from '@angular/core';
// - DOMAIN
import { V1CoilsPanelComponent } from '../../panels/v1-coils-panel/v1-coils-panel.component';

@Component({
    selector: 'v2-coil-list-page',
    templateUrl: './v2-coil-list-page.component.html',
    styleUrls: ['./v2-coil-list-page.component.scss']
})
export class V2CoilListPageComponent {
    @ViewChild(V1CoilsPanelComponent) private coilsPanel: V1CoilsPanelComponent;

    // - R E F R E S H A B L E
    public refresh(): void {
        if (null != this.coilsPanel) this.coilsPanel.refresh();
    }
}
