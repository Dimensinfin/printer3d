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
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { Subscription } from 'rxjs';
import { PartTransformer } from '@domain/transformer/PartTransformer.tranformer';
import { Part } from '@domain/Part.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

@Component({
    selector: 'v2-inventory-part-list-page',
    templateUrl: './v2-inventory-part-list-page.component.html',
    styleUrls: ['./v2-inventory-part-list-page.component.scss']
})
export class V2InventoryPartListPageComponent extends BackgroundEnabledComponent implements OnInit {
    private nodes: Part[] = []
    constructor(
        protected appStore: AppStoreService,
        protected backendService: BackendService) { super(backendService) }


    public ngOnInit(): void {
        console.log(">[V2InventoryPartListPageComponent.ngOnInit]");
        this.setVariant(EVariant.PART_LIST);
        this.refresh();
        console.log("<[V2InventoryPartListPageComponent.ngOnInit]");
    }
    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
    }
    // public getNodes2Render(): ICollaboration[] {
    //     return this.no
    // }
    // - R E F R E S H A B L E
    public refresh(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    this.nodes = response.parts;
                    this.dataModelRoot = response.parts;
                    console.log('-[V2InventoryPartListPageComponent.refresh]> nodes processed: ' + this.dataModelRoot.length);
                    setTimeout(() => {
                        this.completeDowload();    // Notify the completion of the download.
                    }, 3000);
                })
        )
    }
}
