// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { NgDragDropModule } from 'ng-drag-drop';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { ProductionJobListPageComponent } from './pages/production-job-list-page/production-job-list-page.component';
import { V1PendingJobsPanelComponent } from './panels/v1-pending-jobs-panel/v1-pending-jobs-panel.component';
import { V1MachinesPanelComponent } from './panels/v1-machines-panel/v1-machines-panel.component';
import { V2MachinesPanelComponent } from './panels/v2-machines-panel/v2-machines-panel.component';
// import { V1NewRequestDialogComponent } from './dialogs/v1-new-request-dialog/v1-new-request-dialog.component';
import { V1RequestListPageComponent } from './pages/v1-request-list-page/v1-request-list-page.component';
import { V1NewRequestPageComponent } from './pages/v1-new-request-page/v1-new-request-page.component';
import { V1AvailablePartsPanelComponent } from './panels/v1-available-parts-panel/v1-available-parts-panel.component';
import { V1NewRequestPanelComponent } from './panels/v1-new-request-panel/v1-new-request-panel.component';

const routes: Routes = [
    { path: 'requestlist', component: V1RequestListPageComponent },
    { path: 'newrequest', component: V1NewRequestPageComponent },
    { path: 'pendingjobs', component: V1PendingJobsPanelComponent }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(routes),
        // AgGridModule.withComponents([]),
        NgDragDropModule.forRoot(),
        SharedModule,
        RendersModule
    ],
    declarations: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        // V1NewRequestDialogComponent,
        V1RequestListPageComponent,
        V1NewRequestPageComponent,
        V1AvailablePartsPanelComponent,
        V1NewRequestPanelComponent
    ],
    exports: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        // V1NewRequestDialogComponent,
        V1RequestListPageComponent,
        V1NewRequestPageComponent,
        V1AvailablePartsPanelComponent,
        V1NewRequestPanelComponent
    ]
})
export class ProductionModule { }
