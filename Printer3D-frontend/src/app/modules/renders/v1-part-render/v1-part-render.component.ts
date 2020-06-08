// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';

@Component({
    selector: 'v1-part',
    templateUrl: './v1-part-render.component.html',
    styleUrls: ['./v1-part-render.component.scss']
})
export class V1PartRenderComponent extends NodeContainerRenderComponent implements OnDestroy {
    public editPart: Part = new Part();
    public editing: boolean = false;
    protected backendConnections: Subscription[] = [];
    private dataToPartTransformer: ResponseTransformer;

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
        this.dataToPartTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                const targetPart: Part = new Part(entrydata);
                this.isolationService.successNotification('Pieza [' + targetPart.composePartIdentifier() + '] actualizada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK');
                return targetPart;
            });
    }

    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
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
        if (part.active) return 'ACTIVA'
        else return 'FUERA PROD.'
    }
    public isEditing(): boolean {
        return this.editing;
    }
    public toggleEdition(): void {
        this.editing = !this.editing;
        if (this.isEditing())
            this.activateEditing();
        if (!this.isEditing())
            this.closeEditing()
    }
    private activateEditing(): void {
        this.variant = EVariant.EDITABLE_PART;
        this.editPart = new Part(this.node);
    }
    private closeEditing(): void {
        this.variant = EVariant.PART_LIST;
        // this.node = new Part(this.editing);
    }
    private saveEditing(): void {
        console.log('>[V1PartRenderComponent.saveEditing]');
        this.node = new Part(this.editing);
        this.backendConnections.push(
            this.backendService.apiInventoryUpdatePart_v1(this.node as Part, this.dataToPartTransformer)
                .subscribe((updatedPart: Part) => {
                    console.log('-[V1PartRenderComponent.saveEditing]> Updated Part: ' + JSON.stringify(updatedPart))
                })
        );
    }
}
