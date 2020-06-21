// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Job } from '@domain/Job.domain';
import { PartContainer } from '@domain/PartContainer.domain';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';

@Component({
    selector: 'v1-part-container',
    templateUrl: './v1-part-container-render.component.html',
    styleUrls: ['./v1-part-container-render.component.scss']
})
export class V1PartContainerRenderComponent extends NodeContainerRenderComponent {
    public getPartContainer(): PartContainer{
        return this.node as PartContainer
    }
    public getId(): string{
        return this.getPartContainer().getId();
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
}
