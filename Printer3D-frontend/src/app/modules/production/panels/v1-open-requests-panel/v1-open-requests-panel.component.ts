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
import { IPartProvider } from '@domain/interfaces/IPartProvider.interface';
/**
 * To display some of the Request details we should have access to the list of Parts because the Request List from the backend will not have the Part details but only the Part identifier.
 * The Process should lookup on the Parts list for the Part instance.
 */
@Component({
    selector: 'v1-open-requests-panel',
    templateUrl: './v1-open-requests-panel.component.html',
    styleUrls: ['./v1-open-requests-panel.component.scss']
})
export class V1OpenRequestsPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IPartProvider {
    private parts: Part[] = []
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
        this.downloadParts()
    }
    // - I P A R T P R O V I D E R
    public findById(id: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part;
        return undefined;
    }

    // - B A C K E N D
    protected downloadParts(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Parts.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    this.parts = response.getParts();
                    this.downloadRequests();
                })
        )
    }
    protected downloadRequests(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetOpenRequests_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Requests.')
                .setTransformation((entrydata: any): Request[] => {
                    // Extract requests from the response.
                    const requestList: Request[] = []
                    for (let entry of entrydata.requests) {
                        const request: Request = new Request(entry)
                        request.setPartProvider(this)
                        requestList.push(request);
                    }
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
