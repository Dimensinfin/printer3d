// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Job } from '@domain/production/Job.domain';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { V3MachineRenderComponent } from '../v3-machine-render/v3-machine-render.component';

@Component({
    selector: 'v1-pending-job',
    templateUrl: './v1-pending-job-render.component.html',
    styleUrls: ['./v1-pending-job-render.component.scss']
})
export class V1PendingJobRenderComponent extends NodeContainerRenderComponent {
    @Input() machine: V3MachineRenderComponent
    public image = '/assets/media/default-part-image.png'

    public getNode(): Job {
        return this.node as Job
    }
    public getCopies(): string {
        return 'x ' + this.getNode().getCopies()
    }
    public getUniqueId(): string {
        return this.getNode().getId();
    }
    public getPriority(): number {
        return this.getNode().getPriority()
    }
    public getAggregatedNumber(): string {
        return 'x ' + this.getNode().getAggregated()
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
    public isEditable() : boolean{
        if (this.isRunning()) return false
        if (this.machine.state=='COMPLETED') return false
        return true
    }
    public isRunning(): boolean {
        return this.machine.isRunning()
    }
    public onMouseLeave(): void {
        console.log('>[V1PendingJobRenderComponent.onLostFocus]')
        this.machine.changePartCount()
        console.log('<[V1PendingJobRenderComponent.onLostFocus]')
    }
}
