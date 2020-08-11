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
import { INode } from '@domain/interfaces/INode.interface';
import { Model } from '@domain/inventory/Model.domain';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { forIn } from 'cypress/types/lodash';

@Component({
    selector: 'v1-requestable-elements-panel',
    templateUrl: './v1-requestable-elements-panel.component.html',
    styleUrls: ['./v1-requestable-elements-panel.component.scss']
})
export class V1RequestableElementsPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IPartProvider {
    private parts: Part[] = []
    private models: Model[] = []
    private items: ICollaboration[] = []

    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V1OpenRequestsPanelComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V1OpenRequestsPanelComponent.ngOnInit]");
    }

    // - I P A R T P R O V I D E R
    public findById(id: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part;
        return undefined;
    }

    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
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
                    this.downloadModels();
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(new ResponseTransformer()
                .setDescription('Transforms response into a list of Models.')
                .setTransformation((entrydata: any): Model[] => {
                    // For each of the Models expand the Parts from the part provider.
                    const modelList: Model[] = []
                    for (const entry of entrydata.models) {
                        const model: Model = new Model(entry)
                        for (let index = 0; index < entry.partIdList.length; index++) {
                            const partFound = this.findById(entry.partIdList[index])
                            if (undefined != partFound) model.addPart(partFound)
                        }
                        modelList.push(model)
                    }
                    return modelList
                }))
                .subscribe((response: Model[]) => {
                    this.models = response
                    this.generateItems()
                })
        )
    }
    protected generateItems(): void {
        // Join the lsit of Parts and the list of Models in order
        this.items = []
        for (const item of this.models) {
            if (item.isActive()) this.items.push(item)
        }
        for (const item of this.parts) {
            if (item.isActive()) this.items.push(item)
        }
        this.completeDowload(this.items)
    }
}
