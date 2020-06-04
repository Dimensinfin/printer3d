// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
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
import { V1PendingJobsPanelComponent } from '../../panels/v1-pending-jobs-panel/v1-pending-jobs-panel.component';
import { V2MachinesPanelComponent } from '../../panels/v2-machines-panel/v2-machines-panel.component';

@Component({
  selector: 'production-job-list-page',
  templateUrl: './production-job-list-page.component.html',
  styleUrls: ['./production-job-list-page.component.scss']
})
export class ProductionJobListPageComponent implements Refreshable {
    @ViewChild(V1PendingJobsPanelComponent) private jobsPanel: V1PendingJobsPanelComponent;
    @ViewChild(V2MachinesPanelComponent) private machinesPanel: V2MachinesPanelComponent;
    // - R E F R E S H A B L E
    public refresh(): void {
        this.jobsPanel.refresh();
        this.machinesPanel.refresh();
    }
}
