// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/inventory/Part.domain';
import { Coil } from '@domain/inventory/Coil.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto';
import { CoilToUpdateCoilRequestConverter } from '@domain/converter/CoilToUpdateCoilRequest.converter';

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
        protected backendService: BackendService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super();
    }

    public getNode(): Coil {
        return this.node as Coil
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getMaterial(): string {
        return this.getNode().material;
    }
    public getColor(): string {
        return this.getNode().color;
    }
    public getWeight(): string {
        return this.getNode().weight + ' gr.';
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
        const updateCoilRequest: UpdateCoilRequest = new CoilToUpdateCoilRequestConverter().convert(this.editCoil)
        this.backendConnections.push(
            this.backendService.apiInventoryUpdateCoil_v1(updateCoilRequest,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Coil".')
                    .setTransformation((entrydata: any): Coil => {
                        const targetCoil: Coil = new Coil(entrydata);
                        this.isolationService.successNotification('Rollo [' + this.editCoil.getCoilIdentifier() + '] actualizado correctamente.', '/INVENTARIO/ACTUALIZACION ROLLO/OK');
                        return targetCoil;
                    }))
                .subscribe((updatedCoil: Coil) => {
                    this.node = updatedCoil;
                    this.closeEditing();
                    this.ref.detectChanges();
                })
        );
    }
    private activateEditing(): void {
        this.variant = EVariant.COIL_EDITING
        this.editCoil = new Coil(this.node)
    }
    private closeEditing(): void {
        this.editing = false
        this.variant = EVariant.COIL_LIST;
    }
}
