import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Model } from '@domain/inventory/Model.domain';
import { Part } from '@domain/Part.domain';
import { PartStack } from '@domain/PartStack.domain';
import { V1CatalogPanelComponent } from '@app/modules/inventory/panels/v1-catalog-panel/v1-catalog-panel.component';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';

@Component({
    selector: 'v1-model',
    templateUrl: './v1-model-render.component.html',
    styleUrls: ['./v1-model-render.component.scss']
})
export class V1ModelRenderComponent extends NodeContainerRenderComponent {
    public inside: boolean = false
    private editing: boolean = false

    public getNode(): Model {
        return this.node as Model
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getLabel(): string {
        return this.getNode().getLabel()
    }
    public getContentCount(): number {
        let count: number = 0
        for (const content of this.getComposingParts()) {
            count += content.getCount()
        }
        return count
    }
    public getPrice(): string {
        return this.getNode().getPrice() + ' €'
    }
    public getComposingParts(): PartStack[] {
        return this.getNode().getParts()
    }

    // - I N T E R A C T I O N S
    public toggleDisplay(): void {
        this.inside = !this.inside
    }
    public mouseEnter(node: any): void {
        this.inside = true;
    }
    public mouseLeave(node: any): void {
        this.inside = false;
    }
    /**
     * On each iterationpass the current Model node to the selection on the viewer. If editing is closed then remove the selection.
     */
    public toggleEdition(): void {
        console.log('>[V1ModelRenderComponent.toggleEdition]')
        this.editing = !this.editing
        if (this.editing) {
            const container = this.container as IViewer
            this.container.enterSelected(this.getNode())
        } else this.container.enterSelected(undefined)
    }
}
