// - CORE
import { Component } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { V1OpenRequestsPanelComponent } from '../../panels/v1-open-requests-panel/v1-open-requests-panel.component';

@Component({
  selector: 'v1-open-requests-page',
  templateUrl: './v1-open-requests-page.component.html',
  styleUrls: ['./v1-open-requests-page.component.scss']
})
export class V1OpenRequestsPageComponent  {
    @ViewChild(V1OpenRequestsPanelComponent) private openRequestsPanel: V1OpenRequestsPanelComponent;
    // @ViewChild(V2MachinesPanelComponent) private machinesPanel: V2MachinesPanelComponent;
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.openRequestsPanel.refresh();
        // this.machinesPanel.refresh();
    }
}
