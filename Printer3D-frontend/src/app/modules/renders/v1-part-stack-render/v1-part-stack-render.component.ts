import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { PartStack } from '@domain/PartStack.domain';
import { V1DropPartPanelComponent } from '@app/modules/common/v1-drop-part-panel/v1-drop-part-panel.component';

@Component({
    selector: 'v1-part-stack',
    templateUrl: './v1-part-stack-render.component.html',
    styleUrls: ['./v1-part-stack-render.component.scss']
})
export class V1PartStackRenderComponent extends NodeContainerRenderComponent {
    public getNode(): PartStack {
        return this.node as PartStack
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getRequired(): number {
        return this.getNode().getCount()
    }
    public getLabel(): string {
        return this.getNode().label;
    }
    public getMaterial(): string {
        return this.getNode().material;
    }
    public getColor(): string {
        return this.getNode().color;
    }
    public removePart(): void {
        const newRequestPanelAsAny = this.container as any;
        const newRequestPanel = newRequestPanelAsAny as V1DropPartPanelComponent
        newRequestPanel.removePart(this.getNode())
    }
}
