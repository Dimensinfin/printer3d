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
    public toggleEdition(): void {
        console.log('>[V1PartContainerRenderComponent.toggleEdition]')
    }
}
