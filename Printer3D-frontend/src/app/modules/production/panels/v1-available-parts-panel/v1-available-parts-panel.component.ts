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
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { PendingJobListResponse } from '@domain/dto/PendingJobListResponse.dto';
import { Job } from '@domain/Job.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';

@Component({
    selector: 'v1-available-parts-panel',
    templateUrl: './v1-available-parts-panel.component.html',
    styleUrls: ['./v1-available-parts-panel.component.scss']
})
export class V1AvailablePartsPanelComponent extends AppPanelComponent implements OnInit {
    public ngOnInit(): void {
        console.log(">[V2InventoryPartListPageComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[V2InventoryPartListPageComponent.ngOnInit]");
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
        // this.downloadParts();
    }

}
