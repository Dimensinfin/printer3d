// - CORE
import { Component } from '@angular/core';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/inventory/Part.domain';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { RequestItem } from '@domain/production/RequestItem.domain';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';

@Component({
    selector: 'v1-request-item',
    templateUrl: './v1-request-item-render.component.html',
    styleUrls: ['./v1-request-item-render.component.scss']
})
export class V1RequestItemRenderComponent extends NodeContainerRenderComponent {
    public getNode(): RequestItem {
        return this.node as RequestItem
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public hasMissing(): boolean {
        return this.getNode().getMissing() > 0
    }
    public getMissing(): string {
        if (this.hasMissing()) return this.getNode().getMissing() + '';
        else return '-'
    }
    public getRequired(): number {
        return this.getNode().getQuantity()
    }
    public getLabel(): string {
        return this.getNode().getContent().getLabel()
    }
    public getPrice(): string {
        return this.getNode().getPrice() * this.getNode().getQuantity() + " €"
    }
    public getMaterial(): string {
        if (this.getNode().getType() == RequestContentType.PART) {
            const part: Part = this.getNode().getContent() as any
            return part.getMaterial()
        } else return "-"
    }
    public getColor(): string {
        if (this.getNode().getType() == RequestContentType.PART) {
            const part: Part = this.getNode().getContent() as any
            return part.getColor()
        } else return "-"
    }
    // - I N T E R A C T I O N S
    public removeContent(): void {
        const newRequestPanelAsAny = this.container as any;
        const newRequestPanel = newRequestPanelAsAny as V1NewRequestPanelComponent
        newRequestPanel.removeContent(this.getNode())
    }
}
