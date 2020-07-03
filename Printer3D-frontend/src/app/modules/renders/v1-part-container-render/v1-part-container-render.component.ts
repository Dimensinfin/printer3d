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
import { PartContainer } from '@domain/PartContainer.domain';

@Component({
    selector: 'v1-part-container',
    templateUrl: './v1-part-container-render.component.html',
    styleUrls: ['./v1-part-container-render.component.scss']
})
export class V1PartContainerRenderComponent extends NodeContainerRenderComponent {
    public editPart: Part = new Part();
    public editing: boolean = false;

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super();
    }

    public getNode(): PartContainer {
        return this.node as PartContainer
    }
    public getUniqueId(): string {
        return this.getNode().getId();
    }
    public getLabel(): string {
        const part = this.node as PartContainer;
        return part.label;
    }
    public getDescription(): string {
        const part = this.node as PartContainer;
        return part.description;
    }
    public getBuildTime(): string {
        const part = this.node as PartContainer;
        return part.buildTime + ' min.';
    }
    public getWeigth(): string {
        return this.getNode().weight + ' gr.'
    }
    public getImagePath(): string {
        return this.getNode().imagePath
    }
    public getModelPath(): string {
        return this.getNode().modelPath
    }
    public imagePathVisible(): boolean {
        return !this.isEmpty(this.getNode().imagePath)
    }
    public modelVisible(): boolean {
        return !this.isEmpty(this.getNode().modelPath)
    }

    // - EDITING
    public isEditing(): boolean {
        return this.editing;
    }
    public toggleEdition($event?: any): void {
        console.log('>[V1PartContainerRenderComponent.toggleEdition]');
        if (null != $event) $event.stopPropagation()
        this.editing = !this.editing;
        if (this.isEditing())
            this.activateEditing();
        if (!this.isEditing())
            this.closeEditing()
    }
    public saveEditing(): void {
        console.log('>[V1PartContainerRenderComponent.saveEditing]');
        this.node = new Part(this.editPart);
        this.backendConnections.push(
            this.backendService.apiInventoryGroupUpdatePart_v1(this.node as Part,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
                    .setTransformation((entrydata: any): Part => {
                        const targetPart: Part = new Part(entrydata);
                        this.isolationService.successNotification('Pieza [' + targetPart.composePartIdentifier() + '] actualizada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK');
                        return targetPart;
                    }))
                .subscribe((updatedPart: Part) => {
                    // console.log('-[V1PartRenderComponent.saveEditing]> Updated Part: ' + JSON.stringify(updatedPart))
                    this.node = updatedPart;
                    this.toggleEdition();
                    this.ref.detectChanges();
                })
        );
    }
    private activateEditing(): void {
        this.variant = EVariant.EDITING_PART_GROUP;
        this.editPart = new Part(this.node);
    }
    private closeEditing(): void {
        this.variant = EVariant.CATALOG;
    }

}
