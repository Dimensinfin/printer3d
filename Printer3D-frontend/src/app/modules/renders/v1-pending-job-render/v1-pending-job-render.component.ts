// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Job } from '@domain/Job.domain';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';

@Component({
    selector: 'v1-pending-job',
    templateUrl: './v1-pending-job-render.component.html',
    styleUrls: ['./v1-pending-job-render.component.scss']
})
export class V1PendingJobRenderComponent extends NodeContainerRenderComponent {
    public image = '/assets/media/default-part-image.png'

    public getNode(): Job {
        return this.node as Job
    }
    public getUniqueId(): string {
        return this.getNode().getId();
    }
    public getPriority(): number {
        return this.getNode().getPriority()
    }
    public getNumber(): string {
        return 'x ' + this.getNode().getNumber()
    }
    public getLabel(): string {
        return this.getNode().getPart().label;
    }
    public getMaterial(): string {
        return this.getNode().getPart().material;
    }
    public getColor(): string {
        return this.getNode().getPart().color;
    }
    public getBuildTime(): number {
        return this.getNode().getPart().buildTime;
    }
}
