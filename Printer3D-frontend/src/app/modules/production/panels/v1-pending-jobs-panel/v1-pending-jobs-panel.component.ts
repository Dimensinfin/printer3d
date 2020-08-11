// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Job } from '@domain/production/Job.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { environment } from '@env/environment';
import { JobAggregator } from '@domain/JobAggregator.domain';

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
                    const aggregator = new JobAggregator()
                    for (let job of response) // Aggregate equal jobs into a single display element
                        aggregator.addJob(job)
                    this.completeDowload(aggregator.getAggregatedJobs()); // Notify the completion of the download.
                })
        );
    }
}
