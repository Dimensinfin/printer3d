// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { platformconstants } from '@app/platform/platform-constants';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { Feature } from '@domain/Feature.domain';

@Component({
    selector: 'v1-part',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './v1-part-render.component.html',
    styleUrls: ['./v1-part-render.component.scss']
})
export class V1PartRenderComponent extends NodeContainerRenderComponent {
    public editPart: Part = new Part();
    public editing: boolean = false;
    private dataToPartTransformer: ResponseTransformer;

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super();
        this.dataToPartTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                const targetPart: Part = new Part(entrydata);
                this.isolationService.successNotification('Pieza [' + targetPart.composePartIdentifier() + '] actualizada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK');
                return targetPart;
            });
    }

    public getPart(): Part {
        return this.node as Part
    }
    public getUniqueId(): string {
        return this.getPart().getId()
    }
    public getLabel(): string {
        const part = this.node as Part;
        return part.label;
    }
    public getMaterial(): string {
        const part = this.node as Part;
        return part.material;
    }
    public getColor(): string {
        const part = this.node as Part;
        return part.color;
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
        return this.getPart().getAvailable();
    }
    public getActive(): string {
        const part = this.node as Part;
        if (part.active) return 'ACTIVA'
        else return 'FUERA PROD.'
    }
    public isActive(): boolean {
        const part = this.node as Part;
        return part.active;
    }

    // - EDITING
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
    public saveEditing(): void {
        console.log('>[V1PartRenderComponent.saveEditing]');
        this.node = new Part(this.editPart);
        this.backendConnections.push(
            this.backendService.apiInventoryUpdatePart_v1(this.node as Part, this.dataToPartTransformer)
                .subscribe((updatedPart: Part) => {
                    // console.log('-[V1PartRenderComponent.saveEditing]> Updated Part: ' + JSON.stringify(updatedPart))
                    this.node = updatedPart;
                    this.toggleEdition();
                    this.ref.detectChanges();
                })
        );
    }
    /**
     * Save this part on the storage and open the dialog so the fields can be edited.
     */
    public duplicatePart(): void {
        this.isolationService.setToStorageObject(platformconstants.PARTIAL_PART_KEY, this.getNode())
        const targetFeature = new Feature({
            "label": "/Nueva Pieza",
            "enabled": true,
            "active": false,
            "interaction": "DIALOG",
            "route": "NewPartDialog"
        })
        console.log('><[V1PartRenderComponent.duplicatePart]> DIALOG')
        targetFeature.activate();
        const dialogRef = this.dialogFactory.processClick(targetFeature);
        dialogRef.afterClosed()
            .subscribe(result => {
                console.log('[V1FeatureRenderComponent.onClick]> Close detected');
                targetFeature.deactivate();
            });
    }
    private activateEditing(): void {
        this.variant = EVariant.EDITABLE_PART;
        this.editPart = new Part(this.node);
    }
    private closeEditing(): void {
        this.variant = EVariant.PART_LIST;
    }
}
