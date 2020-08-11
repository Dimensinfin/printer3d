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
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Coil } from '@domain/inventory/Coil.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

@Component({
    selector: 'v1-coils-panel',
    templateUrl: './v1-coils-panel.component.html',
    styleUrls: ['./v1-coils-panel.component.scss']
})
export class V1CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V1CoilsPanelComponent.ngOnInit]");
        this.startDownloading();
        this.setVariant(EVariant.COIL_LIST)
        this.refresh();
        console.log("<[V1CoilsPanelComponent.ngOnInit]");
    }
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.downloadCoils()
    }
    // - B A C K E N D
    public downloadCoils(): void {
        console.log(">[V1CoilsPanelComponent.downloadCoils]");
        this.backendConnections.push(
            this.backendService.apiInventoryCoils_v1(new ResponseTransformer().setDescription('Transforms Inventory Coil list form backend.')
                .setTransformation((entrydata: any): CoilListResponse => {
                    return new CoilListResponse(entrydata);
                }))
                .subscribe((response: CoilListResponse) => {
                    const coilList = this.sortCoildByMaterialColor(response.getCoils());
                    console.log('-[V1CoilsPanelComponent.downloadCoils]> Nodes downloaded: ' + coilList.length);
                    this.completeDowload(coilList); // Notify the completion of the download.
                })
        )
        console.log("<[V1CoilsPanelComponent.downloadCoils]");
    }
    private sortCoildByMaterialColor(containers: Coil[]): Coil[] {
        return containers.sort((coil1, coil2) =>
            0 - (coil2.material + coil2.color > coil1.material + coil1.color ? 1 : -1)
        )
    }
}
