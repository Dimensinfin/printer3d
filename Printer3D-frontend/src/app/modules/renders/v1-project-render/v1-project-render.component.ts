// - CORE
import { Component } from '@angular/core'
import { ChangeDetectorRef } from '@angular/core'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { Part } from '@domain/inventory/Part.domain'
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { BackendService } from '@app/services/backend.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { IsolationService } from '@app/platform/isolation.service'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { PartContainer } from '@domain/inventory/PartContainer.domain'
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto'
import { PartToUpdateGroupRequestConverter } from '@domain/converter/PartToUpdateGroupRequest.converter'
import { Project } from '@domain/inventory/Project.domain'

@Component({
    selector: 'v1-project',
    templateUrl: './v1-project-render.component.html',
    styleUrls: ['./v1-project-render.component.scss']
})
export class V1ProjectRenderComponent extends NodeContainerRenderComponent {
    public self: V1ProjectRenderComponent

    public getNode(): Project {
        return this.node as Project
    }
    public getUniqueId(): string {
        return this.getProjectName()
    }
    public getProjectName(): string {
        if(this.getNode())return this.getNode().getName()
        return '-'
    }
    public isExpanded(): boolean {
        if (this.getNode())
            if (this.getNode().isExpandable()) return this.getNode().isExpanded()
        return false
    }
    public toggleEdition($event?: any): void {
        console.log('>[V1PartContainerRenderComponent.toggleEdition]')
    }
    public getContainers(): any[] {
        console.log(this.getNode().getContents().length)
        if (this.getNode()) return this.getNode().getContents()
        else return []
    }
}
