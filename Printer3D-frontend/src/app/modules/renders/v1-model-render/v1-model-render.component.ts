import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Model } from '@domain/inventory/Model.domain';

@Component({
    selector: 'v1-model',
    templateUrl: './v1-model-render.component.html',
    styleUrls: ['./v1-model-render.component.scss']
})
export class V1ModelRenderComponent extends NodeContainerRenderComponent {
    public getNode(): Model {
        return this.node as Model
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getLabel(): string {
        return this.getNode().getLabel()
    }
    public getPrice(): string {
        return this.getNode().getPrice() + ' €'
    }
}
