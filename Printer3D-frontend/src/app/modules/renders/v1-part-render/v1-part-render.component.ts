import { Component, OnInit } from '@angular/core';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';

@Component({
    selector: 'v1-part',
    templateUrl: './v1-part-render.component.html',
    styleUrls: ['./v1-part-render.component.scss']
})
export class V1PartRenderComponent extends NodeContainerRenderComponent {

    constructor() { super(); }

    ngOnInit(): void {
    }
    public getLabel(): string {
        const part = this.node as Part;
        return part.label;
    }
    public getDescription(): string {
        const part = this.node as Part;
        return part.description;
    }
    public getMaterial(): string {
        const part = this.node as Part;
        return part.material;
    }
    public getColor(): string {
        const part = this.node as Part;
        return part.colorCode;
    }
    public getCost(): string {
        const part = this.node as Part;
        return part.cost + '';
    }
    public getPrice(): string {
        const part = this.node as Part;
        return part.price + '';
    }
    public getBuildTime(): string {
        const part = this.node as Part;
        return part.buildTime + '';
    }
    public getStockRequired(): string {
        const part = this.node as Part;
        return part.stockLevel + '';
    }
    public getStockAvailable(): string {
        const part = this.node as Part;
        return part.stockLevel + '';
    }
    public onClick(): void { }
}
