// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartContainer } from '@domain/PartContainer.domain';
import { environment } from '@env/environment';

@Component({
    selector: 'v2-inventory-part-list-page',
    templateUrl: './v2-inventory-part-list-page.component.html',
    styleUrls: ['./v2-inventory-part-list-page.component.scss']
})
export class V2PartListPageComponent extends AppPanelComponent implements OnInit {
    private partContainers: Map<string, PartContainer> = new Map<string, PartContainer>();

    constructor(
        protected appStore: AppStoreService,
        protected backendService: BackendService) { super() }

    public ngOnInit(): void {
        console.log(">[V2InventoryPartListPageComponent.ngOnInit]");
        this.startDownloading();
        this.setVariant(EVariant.PART_LIST);
        this.refresh();
        console.log("<[V2InventoryPartListPageComponent.ngOnInit]");
    }
    protected downloadParts():void{
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
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
                    // Load the containers on the root for the MVC.
                    const containers = this.partContainers.values();
                    const sortedContainers: PartContainer[] = []
                    this.dataModelRoot = []
                    for (const container of containers)
                        sortedContainers.push(container)
                    console.log('-[V2InventoryPartListPageComponent.refresh]> nodes processed: ' + this.dataModelRoot.length);
                    // if (!environment.production)
                    //     setTimeout(() => { // This is only for development
                            this.completeDowload(this.sortPartContainersByLabel(sortedContainers)); // Notify the completion of the download.
                        // }, 2000);
                })
        )
    }
    // - R E F R E S H A B L E
    /**
     * Restart component contents before a refresh.
     */
    public clean(): void {
        this.partContainers = new Map<string, PartContainer>();
    }
    /**
     * When the page gets the list of Parts it should scan it and generate a list of Part Containers with distinct labels. Inside that containers there will be the Parts, each one with their different configurations.
     * Part containers will be ordered by their active status. Active parts will be listed before inactive groups.
     */
    public refresh(): void {
        this.clean();
        this.downloadParts();
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
