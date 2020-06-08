// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';

@Component({
    selector: 'v1-part',
    templateUrl: './v1-part-render.component.html',
    styleUrls: ['./v1-part-render.component.scss']
})
export class V1PartRenderComponent extends NodeContainerRenderComponent {

    constructor() { super(); }

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
        return part.cost + ' €';
    }
    public getPrice(): string {
        const part = this.node as Part;
        return part.price + ' €';
    }
    public getStockRequired(): number {
        const part = this.node as Part;
        return part.stockLevel;
    }
    public getStockAvailable(): number {
        const part = this.node as Part;
        return part.stockAvailable;
    }
    public getActive(): string {
        const part = this.node as Part;
        if ( part.active ) return 'ACTIVA'
        else return 'FUERA PROD.'
    }
}
