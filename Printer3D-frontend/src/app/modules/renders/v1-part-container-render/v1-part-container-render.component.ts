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
import { PartContainer } from '@domain/PartContainer.domain'
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto'
import { PartToUpdateGroupRequestConverter } from '@domain/converter/PartToUpdateGroupRequest.converter'

@Component({
    selector: 'v1-part-container',
    templateUrl: './v1-part-container-render.component.html',
    styleUrls: ['./v1-part-container-render.component.scss']
})
export class V1PartContainerRenderComponent extends NodeContainerRenderComponent {
    public editPart: Part = new Part()
    public editing: boolean = false
    public self: V1PartContainerRenderComponent

    constructor(
        protected isolationService: IsolationService,
        protected backendService: BackendService,
        protected ref: ChangeDetectorRef,
        protected dialogFactory: DialogFactoryService) {
        super()
        this.self = this
    }

    public getNode(): PartContainer {
        return this.node as PartContainer
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getLabel(): string {
        return this.getNode().label
    }
    public getDescription(): string {
        return this.getNode().description
    }
    public getBuildTime(): string {
        const part = this.node as PartContainer
        return part.buildTime + ' min.'
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
    public isExpanded(): boolean {
        if (this.getNode())
            if (this.getNode().isExpandable()) return this.getNode().isExpanded()
        return false
    }
    public getParts(): Part[] {
        if (this.getNode()) return this.sortPartByColour(this.getNode().contents)
        else return []
    }

    // - I N T E R A C T I O N S
    public notifyDataChanged () : void {
        this.ref.detectChanges();
    }

    // - E D I T I N G
    public isEditing(): boolean {
        return this.editing
    }
    public toggleEdition($event?: any): void {
        console.log('>[V1PartContainerRenderComponent.toggleEdition]')
        if (null != $event) $event.stopPropagation()
        this.editing = !this.editing
        if (this.isEditing())
            this.activateEditing()
        if (!this.isEditing())
            this.closeEditing()
    }
    public saveEditing(): void {
        console.log('>[V1PartContainerRenderComponent.saveEditing]')
        const updateGroupRequest: UpdateGroupRequest = new PartToUpdateGroupRequestConverter().convert(this.editPart)
        this.backendConnections.push(
            this.backendService.apiInventoryGroupUpdatePart_v1(updateGroupRequest,
                new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
                    .setTransformation((entrydata: any): Part => {
                        const targetPart: Part = new Part(entrydata)
                        this.isolationService.successNotification('Grupo de piezas [' + updateGroupRequest.label + '] actualizada correctamente.', '/INVENTARIO/ACTUALIZACION GRUPO/OK')
                        return targetPart
                    }))
                .subscribe((unrequired: any) => {
                    this.closeEditing()
                    this.updateNode()
                    this.ref.detectChanges()
                })
        )
    }
    private activateEditing(): void {
        this.variant = EVariant.EDITING_PART_GROUP
        this.editPart = new Part(this.node)
    }
    private closeEditing(): void {
        this.variant = EVariant.CATALOG
    }
    private updateNode(): void {
        console.log('>[V1PartContainerRenderComponent.updateNode]')
        const containerAsAny = this.getNode() as any
        console.log('>[V1PartContainerRenderComponent.updateNode]> editPart.description: ' + this.editPart.description)
        console.log('>[V1PartContainerRenderComponent.updateNode]> containerAsAny.description: ' + containerAsAny.description)
        containerAsAny.description = this.editPart.description
        containerAsAny.buildTime = this.editPart.buildTime
        containerAsAny.weight = this.editPart.weight
        containerAsAny.imagePath = this.editPart.imagePath
        containerAsAny.modelPath = this.editPart.modelPath
    }
    private sortPartByColour(parts: Part[]): Part[] {
        return parts.sort((part1, part2) =>
            0 - (!part2.active + '.' + part2.material + '/' + part2.color > !part1.active + '.' + part1.material + '/' + part1.color ? 1 : -1)
        )
    }
}
