// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
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
    public clean(): void {
    }
    public refresh(): void {
        this.jobsPanel.refresh();
        this.machinesPanel.refresh();
    }
}
