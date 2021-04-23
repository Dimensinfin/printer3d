// - CORE
import { Component } from '@angular/core'
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component'
import { Project } from '@domain/inventory/Project.domain'

@Component({
    selector: 'v1-project',
    templateUrl: './v1-project-render.component.html',
    styleUrls: ['./v1-project-render.component.scss']
})
export class V1ProjectRenderComponent extends NodeContainerRenderComponent {
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
    public getContainers(): any[] {
        if (this.getNode()) return this.getNode().getContents()
        else return []
    }
}
