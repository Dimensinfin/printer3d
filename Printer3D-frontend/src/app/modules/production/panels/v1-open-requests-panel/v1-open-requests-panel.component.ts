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
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/Part.domain';
import { Request } from '@domain/Request.domain';
import { IPartProvider } from '@domain/interfaces/IPartProvider.interface';
import { V1OpenRequestsPageComponent } from '../../pages/v1-open-requests-page/v1-open-requests-page.component';
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
    @Input() page: V1OpenRequestsPageComponent;
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

    // - I V I E W E R
    public fireSelectionChanged(): void {
        const request = this.target as Request;
        this.page.selectRequest(request);
    }
    // - E V E N T S
    public selectRequest(request: Request): void {
        this.page.selectRequest(request);
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
                    this.completeDowload(requestList); // Notify the completion of the download.
                })
        )
    }
}
