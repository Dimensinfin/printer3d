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
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { environment } from '@env/environment';

@Component({
    selector: 'v1-pending-jobs-panel',
    templateUrl: './v1-pending-jobs-panel.component.html',
    styleUrls: ['./v1-pending-jobs-panel.component.scss']
})
export class V1PendingJobsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    public active: boolean = false;
    public jobs: Job[] = [];

    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V1PendingJobsPanelComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V1PendingJobsPanelComponent.ngOnInit]");
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.active = true;
        this.jobs = [];
    }
    public refresh(): void {
        this.clean();
        this.getJobs();
    }

    // - B A C K E N D
    private getJobs(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetJobs_v1(new ResponseTransformer()
                .setDescription('Do HTTP transformation to "PendingJobListResponse".')
                .setTransformation((entrydata: any): Job[] => {
                    const jobs: Job[] = []
                    entrydata.forEach(element => {
                        jobs.push(new Job(element));
                    });
                    return jobs;
                }))
                .subscribe((response: Job[]) => {
                    if (!environment.production)
                        // setTimeout(() => { // This is only for development
                            this.completeDowload(response); // Notify the completion of the download.
                        // }, 1000);
                    // Process the response to extract the Machines to the render list
                    // this.jobs = response;
                })
        );
    }
}
