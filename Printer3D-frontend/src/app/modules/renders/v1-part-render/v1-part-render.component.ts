// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
import { OnDestroy } from '@angular/core'
import { Input } from '@angular/core'
import { ChangeDetectionStrategy } from '@angular/core'
import { ChangeDetectorRef } from '@angular/core'
import { Subscription } from 'rxjs'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { Part } from '@domain/inventory/Part.domain'
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { BackendService } from '@app/services/backend.service'
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { IsolationService } from '@app/platform/isolation.service'
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { Feature } from '@domain/Feature.domain'
import { V1PartContainerRenderComponent } from '../v1-part-container-render/v1-part-container-render.component'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'

@Component({
    selector: 'v1-part',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './v1-part-render.component.html',
    styleUrls: ['./v1-part-render.component.scss']
})
export class V1PartRenderComponent extends NodeContainerRenderComponent {
    public editPart: Part = new Part()
    public editing: boolean = false

    constructor(
        protected isolationService: IsolationService,
        protected inventoryService: InventoryService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super()
    }

    public getNode(): Part {
        return this.node as Part
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getLabel(): string {
        return this.getNode().label
    }
    public getMaterial(): string {
        return this.getNode().material
    }
    public getColor(): string {
        return this.getNode().color
    }
    public getCost(): string {
        return this.getNode().cost + ' €'
    }
    public getPrice(): string {
        return this.getNode().price + ' €'
    }
    public getStockRequired(): number {
        return this.getNode().stockLevel
    }
    public getStockAvailable(): number {
        return this.getNode().getAvailable()
    }
    public getActive(): string {
        if (this.getNode())
            if (this.getNode().isActive()) return 'ACTIVA'
        return 'FUERA PROD.'
    }
    public isActive(): boolean {
        return this.getNode().isActive()
    }

    // - EDITING
    public isEditing(): boolean {
        return this.editing
    }
    public toggleEdition(): void {
        this.editing = !this.editing
        if (this.isEditing())
            this.activateEditing()
        if (!this.isEditing())
            this.closeEditing()
    }
    public saveEditing(): void {
        console.log('>[V1PartRenderComponent.saveEditing]')
        // Update values that depend on Part final state. [D3D20.11]-When a part is deactivated then the stock count should be set to 0.
        if (!this.editPart.isActive())
            this.editPart.stockLevel = 0
        this.backendConnections.push(
            this.inventoryService.apiv1_InventoryUpdatePart(this.editPart)
                .subscribe((updatedPart: Part) => {
                    this.node = updatedPart
                    this.closeEditing()
                    this.ref.detectChanges()
                    //    this.toggleEdition()
                    // this.ref.detectChanges()
                    // const intermediate = this.container as any
                    // const parent = intermediate as V1PartContainerRenderComponent
                    // parent.notifyDataChanged() // Notify the Part Container about the end of the change.
                })
        )
    }
    /**
     * Save this part on the storage and open the dialog so the fields can be edited.
     */
    public duplicatePart(): void {
        this.isolationService.setToStorageObject(Printer3DConstants.PARTIAL_PART_KEY, this.getNode())
        const targetFeature = new Feature({
            "label": "/Nueva Pieza",
            "enabled": true,
            "active": false,
            "interaction": "DIALOG",
            "route": "NewPartDialog"
        })
        console.log('><[V1PartRenderComponent.duplicatePart]> DIALOG')
        targetFeature.activate()
        const dialogRef = this.dialogFactory.processClick(targetFeature)
        dialogRef.afterClosed()
            .subscribe(result => {
                console.log('[V1FeatureRenderComponent.onClick]> Close detected')
                targetFeature.deactivate()
            })
    }
    private activateEditing(): void {
        this.variant = EVariant.EDITABLE_PART
        this.editPart = new Part(this.node)
    }
    private closeEditing(): void {
        this.variant = EVariant.CATALOG
    }
}
