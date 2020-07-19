// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { V1AvailablePartsPanelComponent } from '../../panels/v1-available-parts-panel/v1-available-parts-panel.component';

@Component({
  selector: 'v1-new-model-page',
  templateUrl: './v1-new-model-page.component.html',
  styleUrls: ['./v1-new-model-page.component.scss']
})
export class V1NewModelPageComponent   implements Refreshable{
    @ViewChild(V1AvailablePartsPanelComponent) private availablePartsPanel: V1AvailablePartsPanelComponent;
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        if (null != this.availablePartsPanel) this.availablePartsPanel.refresh();
    }
}
