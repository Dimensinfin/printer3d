// - CORE
import { Component, ViewChild } from '@angular/core';
// - DOMAIN
import { V1CatalogPanelComponent } from '../../panels/v1-catalog-panel/v1-catalog-panel.component';
import { Model } from '@domain/inventory/Model.domain';
import { V1NewModelPanelComponent } from '../../panels/v1-new-model-panel/v1-new-model-panel.component';

@Component({
    selector: 'v3-inventory-page',
    templateUrl: './v3-inventory-page.component.html',
    styleUrls: ['./v3-inventory-page.component.scss']
})
export class V3InventoryPageComponent {
    @ViewChild(V1CatalogPanelComponent) private catalogPanel: V1CatalogPanelComponent;
    @ViewChild(V1NewModelPanelComponent) private modelEditingPanel: V1NewModelPanelComponent;
    public selected: Model
    public self: V3InventoryPageComponent

    constructor() {
        this.self = this
    }

    // - I N T E R A C T I O N S
    public setSelected(newSelection: Model) {
        console.log('>[V3InventoryPageComponent.setSelected]> Label: ' + newSelection.getLabel())
        this.selected = newSelection
        if (null != this.modelEditingPanel) this.modelEditingPanel.startEditing(newSelection)
    }

    public closeEditor(): void {
        if (null != this.modelEditingPanel) this.modelEditingPanel.stopEditing()
    }
    // - R E F R E S H A B L E
    public refresh(): void {
        this.selected = undefined
        if (null != this.catalogPanel) this.catalogPanel.refresh();
    }
}
