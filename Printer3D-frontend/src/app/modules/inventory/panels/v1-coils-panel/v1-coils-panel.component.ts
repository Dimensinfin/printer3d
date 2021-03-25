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
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Coil } from '@domain/inventory/Coil.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { IsolationService } from '@app/platform/isolation.service';
import { InventoryService } from '../../service/inventory.service';

@Component({
    selector: 'v1-coils-panel',
    templateUrl: './v1-coils-panel.component.html',
    styleUrls: ['./v1-coils-panel.component.scss']
})
export class V1CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(
        protected isolationService: IsolationService,
        protected inventoryService: InventoryService) {
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
            this.inventoryService.apiv2_InventoryGetCoils()
                .subscribe((coilList: Coil[]) => {
                    console.log('-[V1CoilsPanelComponent.downloadCoils]> Nodes downloaded: ' + coilList.length);
                    this.completeDowload(this.sortCoildByMaterialColor(coilList)); // Notify the completion of the download.
                }, (error) => {
                    console.log(JSON.stringify(error))
                    console.log('-[V1CoilsPanelComponent.downloadCoils.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
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
