// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { NgDragDropModule } from 'ng-drag-drop';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { ProductionJobListPageComponent } from './pages/production-job-list-page/production-job-list-page.component';
import { V1PendingJobsPanelComponent } from './panels/v1-pending-jobs-panel/v1-pending-jobs-panel.component';
import { V1MachinesPanelComponent } from './panels/v1-machines-panel/v1-machines-panel.component';
import { V2MachinesPanelComponent } from './panels/v2-machines-panel/v2-machines-panel.component';
// import { V1BuildCountdownTimerPanelComponent } from './panels/v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        NgDragDropModule.forRoot(),
        SharedModule,
        RendersModule
    ],
    declarations: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        // V1BuildCountdownTimerPanelComponent
    ],
    exports: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        // V1BuildCountdownTimerPanelComponent
    ]
})
export class ProductionModule { }
