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
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/inventory/Part.domain';

@Component({
    selector: 'v1-available-parts-panel',
    templateUrl: './v1-available-parts-panel.component.html',
    styleUrls: ['./v1-available-parts-panel.component.scss']
})
export class V1AvailablePartsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V2InventoryPartListPageComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V2InventoryPartListPageComponent.ngOnInit]");
    }

    protected generatePartListing(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    this.completeDowload(this.sortPartsByLabel(response.getParts())); // Notify the completion of the download.
                })
        )
    }
    // - R E F R E S H A B L E
    /**
     * Restart component contents before a refresh.
     */
    public clean(): void {
        // this.partContainers = new Map<string, PartContainer>();
    }
    /**
     * When the page gets the list of Parts it should scan it and generate a list of Part Containers with distinct labels. Inside that containers there will be the Parts, each one with their different configurations.
     * Part containers will be ordered by their active status. Active parts will be listed before inactive groups.
     */
    public refresh(): void {
        this.clean();
        this.generatePartListing();
    }
    private sortPartsByLabel(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part2.label == part1.label)
                return this.orderByMaterial(part1, part2);
            return 0 - (part2.label > part1.label ? 1 : -1);
        })
    }
    private orderByMaterial(part1: Part, part2: Part): number {
        if (part1.material == part2.material)
            return this.orderByColor(part1, part2);
        else
            return 0 - (part2.material > part1.material ? 1 : -1);
    }
    private orderByColor(part1: Part, part2: Part): number {
        return 0 - (part2.color > part1.color ? 1 : -1);
    }
}
