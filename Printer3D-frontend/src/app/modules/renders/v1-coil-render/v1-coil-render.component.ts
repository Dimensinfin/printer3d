// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
import { OnDestroy } from '@angular/core'
import { Input } from '@angular/core'
import { ChangeDetectionStrategy } from '@angular/core'
import { ChangeDetectorRef } from '@angular/core'
import { Subscription } from 'rxjs'
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service'
import { BackendService } from '@app/services/backend.service'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { Part } from '@domain/inventory/Part.domain'
import { Coil } from '@domain/inventory/Coil.domain'
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto'
import { CoilToUpdateCoilRequestConverter } from '@domain/converter/CoilToUpdateCoilRequest.converter'
import { environment } from '@env/environment'
import { HttpErrorResponse } from '@angular/common/http'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'

@Component({
    selector: 'v1-coil',
    templateUrl: './v1-coil-render.component.html',
    styleUrls: ['./v1-coil-render.component.scss']
})
export class V1CoilRenderComponent extends NodeContainerRenderComponent {
    public editCoil: Coil = new Coil()
    public editing: boolean = false

    constructor(
        protected isolationService: IsolationService,
        protected inventoryService: InventoryService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super()
    }

    public getNode(): Coil {
        return this.node as Coil
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getMaterial(): string {
        return this.getNode().material
    }
    public getColor(): string {
        return this.getNode().color
    }
    public getColorSetGroup(): string {
        if (this.getNode()) return this.getNode().label
        else return '-'
    }
    public getWeight(): string {
        if (this.getNode()) return this.getNode().weight + ' gr.'
        else return '- gr.'
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
        this.backendConnections.push(
            this.inventoryService.apiv2_InventoryUpdateCoil(this.editCoil)
                .subscribe((updatedCoil: Coil) => {
                    this.node = updatedCoil
                    this.closeEditing()
                    this.ref.detectChanges()
                }, (error) => {
                    console.log('-[V1CoilsPanelComponent.downloadCoils.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        )
    }
    private activateEditing(): void {
        this.variant = EVariant.COIL_EDITING
        this.editCoil = new Coil(this.node)
    }
    private closeEditing(): void {
        this.editing = false
        this.variant = EVariant.COIL_LIST
    }
}
