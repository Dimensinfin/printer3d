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

@Component({
  selector: 'v1-pending-job-render',
  templateUrl: './v1-pending-job-render.component.html',
  styleUrls: ['./v1-pending-job-render.component.scss']
})
export class V1PendingJobRenderComponent implements OnInit {
    @Input() node: Job;

    constructor() { }

    ngOnInit(): void {
        if ( null == this.image)this.image = '/assets/media/default-part-image.png'
    }

}
