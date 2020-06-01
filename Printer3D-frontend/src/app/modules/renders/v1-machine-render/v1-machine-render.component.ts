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
import { Part } from '@domain/Part.domain';
import { Job } from '@domain/Job.domain';

@Component({
    selector: 'v1-machine-render',
    templateUrl: './v1-machine-render.component.html',
    styleUrls: ['./v1-machine-render.component.scss']
})
export class V1MachineRenderComponent {
    @Input() node: Machine;
    public target: Job;

    public onDrop(drop: any) {
        // const data: Job = drop.dragData;
        this.target=drop.dragData;
    }
}
