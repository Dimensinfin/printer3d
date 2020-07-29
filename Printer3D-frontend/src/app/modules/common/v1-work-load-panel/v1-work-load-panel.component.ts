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
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { Job } from '@domain/Job.domain';

/**
 * This panel aggregates all the pending jobs time and displays all that time on the HM format.
 */
@Component({
    selector: 'v1-work-load-panel',
    templateUrl: './v1-work-load-panel.component.html',
    styleUrls: ['./v1-work-load-panel.component.scss']
})
export class V1WorkLoadPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    private workTime: number = 0

    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V1WorkLoadPanelComponent.ngOnInit]");
        this.refresh();
        console.log("<[V1WorkLoadPanelComponent.ngOnInit]");
    }

    public getWorkLoad(): string {
        const time = this.workTime
        const hours = Math.floor(time / 3600);
        const minutes = Math.floor((time - hours * 3600) / 60)
        return hours + 'h' + minutes + 'm'
    }
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.getJobs()
    }
    // - B A C K E N D
    private getJobs(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetJobs_v1(new ResponseTransformer()
                .setDescription('Do HTTP transformation to "Job" list.')
                .setTransformation((entrydata: any): Job[] => {
                    const jobs: Job[] = []
                    entrydata.forEach(element => {
                        jobs.push(new Job(element));
                    });
                    return jobs;
                }))
                .subscribe((response: Job[]) => {
                    this.workTime = 0
                    for (let job of response) // Aggregate equal jobs into a single display element
                        this.workTime += job.getBuildSeconds()
                })
        );
    }
}
