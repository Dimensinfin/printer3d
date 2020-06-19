// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { Job } from '@domain/Job.domain';
import { Part } from '@domain/Part.domain';
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';

@Component({
    selector: 'v1-pending-job',
    templateUrl: './v1-pending-job-render.component.html',
    styleUrls: ['./v1-pending-job-render.component.scss']
})
export class V1PendingJobRenderComponent extends NodeContainerRenderComponent {
    public image = '/assets/media/default-part-image.png'

    public getUniqueId(): string {
        const job = this.node as Job
        return job.getId();
    }
    public getJob(): Job {
        const job = this.node as Job
        return job;
    }
    public getLabel(): string {
        const job = this.node as Job
        return job.getPart().label;
    }
    public getMaterial(): string {
        const job = this.node as Job
        return job.getPart().material;
    }
    public getColor(): string {
        const job = this.node as Job
        return job.getPart().color;
    }
    public getBuildTime(): number {
        const job = this.node as Job
        return job.getPart().buildTime;
    }
}
