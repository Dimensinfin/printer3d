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
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/Part.domain';
import { Request } from '@domain/Request.domain';

@Component({
    selector: 'v1-open-requests-panel',
    templateUrl: './v1-open-requests-panel.component.html',
    styleUrls: ['./v1-open-requests-panel.component.scss']
})
export class V1OpenRequestsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V1OpenRequestsPanelComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V1OpenRequestsPanelComponent.ngOnInit]");
    }

    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.downloadRequests()
    }

    // - B A C K E N D
    protected downloadRequests(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetOpenRequests_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Requests.')
                .setTransformation((entrydata: any): Request[] => {
                    // Extract requests from the response.
                    const requestList: Request[] = []
                    for (let entry of entrydata.requests)
                        requestList.push(new Request(entry));
                    return requestList;
                }))
                .subscribe((requestList: Request[]) => {
                    // Show the list of Parts ordered by label/material/color
                    if (!environment.production)
                        setTimeout(() => { // This is only for development
                            this.completeDowload(requestList); // Notify the completion of the download.
                        }, 1000);
                })
        )
    }

}
