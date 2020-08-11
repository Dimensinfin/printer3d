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
import { Part } from '@domain/inventory/Part.domain';
import { Request } from '@domain/Request.domain';
import { IPartProvider } from '@domain/interfaces/IPartProvider.interface';
import { V1OpenRequestsPageComponent } from '../../pages/v1-open-requests-page/v1-open-requests-page.component';
import { Model } from '@domain/inventory/Model.domain';
import { DataToRequestConverter } from '@domain/converter/DataToRequest.converter';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { IContent } from '@domain/interfaces/IContent.interface';
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
    private models: Model[] = []

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
        this.page.selectRequest(this.selection.getFirstSelected() as Request);
    }
    // - E V E N T S
    public selectRequest(request: Request): void {
        this.page.selectRequest(request);
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.parts = []
        this.models = []
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
    }
    // - I P A R T P R O V I D E R
    /**
     * Now identifier can belong to Parts or Models, the second parameter determined the type for the search.
     * @param id The item identifier to search
     */
    public findById(id: string, type: RequestContentType): IContent {
        if (type == RequestContentType.PART)
            for (let part of this.parts)
                if (part.getId() == id) return part;
        if (type == RequestContentType.MODEL)
            for (let model of this.models)
                if (model.getId() == id) return model;
        return undefined;
    }

    // - B A C K E N D
    protected downloadParts(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Parts.')
                .setTransformation((entrydata: any): PartListResponse => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadParts]>Processing Parts')
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    this.parts = response.getParts();
                    this.downloadModels();
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Models.')
                .setTransformation((entrydata: any): Model[] => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadModels]> Processing Models')
                    // For each of the Models expand the Parts from the part provider.
                    const modelList: Model[] = []
                    for (const entry of entrydata.models) {
                        const model: Model = new Model(entry)
                        for (let index = 0; index < entry.partIdList.length; index++) {
                            const partFound = this.findById(entry.partIdList[index], RequestContentType.PART)
                            if (undefined != partFound) model.addPart(partFound as Part)
                        }
                        modelList.push(model)
                    }
                    return modelList
                }))
                .subscribe((response: Model[]) => {
                    this.models = response
                    this.downloadRequests()
                })
        )
    }
    protected downloadRequests(): void {
        this.backendConnections.push(
            this.backendService.apiProductionGetOpenRequests_v2(new ResponseTransformer()
                .setDescription('Transforms response into a list of Requests.')
                .setTransformation((entrydata: any): Request[] => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Processing Requests')
                    // Extract requests from the response and convert them to the Request V2 format. Resolve contents id references.
                    const requestList: Request[] = []
                    const requestConverter: DataToRequestConverter = new DataToRequestConverter(this)
                    for (let index = 0; index < entrydata.length; index++) {
                        requestList.push(requestConverter.convert(entrydata[index]));
                    }
                    return requestList;
                }))
                .subscribe((requestList: Request[]) => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Requests received: ' + requestList.length)
                    this.completeDowload(requestList); // Notify the completion of the download.
                })
        )
    }
}
