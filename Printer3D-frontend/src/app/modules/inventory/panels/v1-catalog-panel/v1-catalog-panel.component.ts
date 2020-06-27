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
import { INode } from '@domain/interfaces/INode.interface';
import { Model } from '@domain/inventory/Model.domain';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { forIn } from 'cypress/types/lodash';
import { PartContainer } from '@domain/PartContainer.domain';
import { V3InventoryPageComponent } from '../../pages/v3-inventory-page/v3-inventory-page.component';

@Component({
    selector: 'v1-catalog-panel',
    templateUrl: './v1-catalog-panel.component.html',
    styleUrls: ['./v1-catalog-panel.component.scss']
})
export class V1CatalogPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IPartProvider {
    @Input() page: V3InventoryPageComponent  // Pointer to the page that contains this panel. Uset as a way to connect to siblings
    private parts: Part[] = []
    private models: Model[] = []
    private partContainers: Map<string, PartContainer> = new Map<string, PartContainer>()
    private items: ICollaboration[] = []

    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V3InventoryPageComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V3InventoryPageComponent.ngOnInit]");
    }

    // - I P A R T P R O V I D E R
    public findById(id: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part;
        return undefined;
    }

    // - I V I E W E R
    /**
     * This method is called whenever the selection changes. Used to spread the event or perform other actions.
     */
    public fireSelectionChanged(): void {
        if (null != this.page)
            if (this.target instanceof Model)
                this.page.setSelected(this.target as Model)
            else this.page.closeEditor()
    }
    // - R E F R E S H A B L E
    public clean(): void {
        this.target = undefined // Clear the selection
        this.parts = []
        this.models = []
        this.partContainers = new Map<string, PartContainer>()
        this.items = []
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
    }
    // - B A C K E N D
    protected downloadParts(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    this.parts = response.getParts();
                    // Sort the Parts before storing them inside the containers.
                    const partList = this.sortPartsByActive(response.getParts());
                    // Classify Parts on part containers.
                    partList.forEach(element => {
                        let hit = this.partContainers.get(element.label);
                        if (null == hit) {
                            hit = new PartContainer(element);
                            this.partContainers.set(element.label, hit);
                        }
                        hit.addPart(element);
                    });
                    this.downloadModels()
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
                    // Join the list of Parts and the list of Models in order
                    this.items = []
                    for (const item of this.models) {
                        this.items.push(item)
                    }
                    // Load the containers on the root for the MVC.
                    const containers = this.partContainers.values();
                    const sortedContainers: PartContainer[] = []
                    for (const container of containers)
                        sortedContainers.push(container)
                    for (const container of this.sortPartContainersByLabel(sortedContainers)) {
                        this.items.push(container)
                    }
                    this.completeDowload(this.items)
                })
        )
    }
    private sortPartsByActive(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part1.active == part2.active) return 0;
            if (part1.active && !part2.active) return -1;
            if (!part1.active && part2.active) return 1;
            return 0;
        })
    }
    private sortPartContainersByLabel(containers: PartContainer[]): PartContainer[] {
        return containers.sort((container1, container2) =>
            0 - (container2.label > container1.label ? 1 : -1)
        )
    }

}
