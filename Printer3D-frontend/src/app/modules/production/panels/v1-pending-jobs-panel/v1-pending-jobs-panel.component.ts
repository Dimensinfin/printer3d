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
import { PendingJobListResponse } from '@domain/dto/PendingJobListResponse.dto';
import { Job } from '@domain/Job.domain';

@Component({
    selector: 'v1-pending-jobs-panel',
    templateUrl: './v1-pending-jobs-panel.component.html',
    styleUrls: ['./v1-pending-jobs-panel.component.scss']
})
export class V1PendingJobsPanelComponent implements OnInit, OnDestroy {
    private backendConnections: Subscription[] = [];
    public active:boolean=false;
    public jobs: Job[] = [];

    constructor(private backendService: BackendService) { }

    public ngOnInit(): void {
        this.getJobs();
    }
    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
    }
    private getJobs(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetPendingJobs_v1(new ResponseTransformer()
                .setDescription('Do HTTP transformation to "PendingJobListResponse".')
                .setTransformation((entrydata: any): PendingJobListResponse => {
                    return new PendingJobListResponse(entrydata);
                }))
                .subscribe((response: PendingJobListResponse) => {
                    // Process the response to extract the Machines to the render list
                    this.jobs = response.getJobs();
                })
        );

    }
}
