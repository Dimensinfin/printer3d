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
import { Coil } from '@domain/Coil.domain';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';

@Component({
  selector: 'v2-coil-list-page',
  templateUrl: './v2-coil-list-page.component.html',
  styleUrls: ['./v2-coil-list-page.component.scss']
})
export class V2CoilListPageComponent extends AppPanelComponent implements OnInit {
    private partContainers: Map<string, PartContainer> = new Map<string, PartContainer>();
    constructor(
        protected appStore: AppStoreService,
        protected backendService: BackendService) { super() }


    public ngOnInit(): void {
        console.log(">[V2CoilListPageComponent.ngOnInit]");
        this.startDownloading();
        this.setVariant(EVariant.COIL_LIST);
        this.refresh();
        console.log("<[V2CoilListPageComponent.ngOnInit]");
    }
    public downloadCoils(): void {
        console.log(">[V2CoilListPageComponent.downloadCoils]");
        this.backendConnections.push(
            this.backendService.apiInventoryCoils_v1(new ResponseTransformer().setDescription('Transforms Inventory Coil list form backend.')
                .setTransformation((entrydata: any): CoilListResponse => {
                    return new CoilListResponse(entrydata);
                }))
                .subscribe((response: CoilListResponse) => {
                    const coilList = this.sortCoildByMaterialColor(response.getCoils());
                    console.log('-[V2CoilListPageComponent.downloadCoils]> Nodes downloaded: ' + coilList.length);
                    // if (!environment.production)
                    //     setTimeout(() => { // This is only for development
                            this.completeDowload(coilList); // Notify the completion of the download.
                        // }, 1000);
                })
        )
        console.log("<[V2CoilListPageComponent.downloadCoils]");
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
        this.downloadCoils();
    }
    private sortCoildByMaterialColor(containers: Coil[]): Coil[] {
        return containers.sort((coil1, coil2) =>
            0 - (coil2.material+coil2.color > coil1.material+coil1.color ? 1 : -1)
        )
    }
}
