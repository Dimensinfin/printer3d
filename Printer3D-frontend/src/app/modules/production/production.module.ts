// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
// import { V1MachinesPanelComponent } from './panels/v1-machines-panel/v1-machines-panel.component';
import { V2MachinesPanelComponent } from './panels/v2-machines-panel/v2-machines-panel.component';
import { V1NewRequestPageComponent } from './pages/v1-new-request-page/v1-new-request-page.component';
import { V1AvailablePartsPanelComponent } from './panels/v1-available-parts-panel/v1-available-parts-panel.component';
import { V1NewRequestPanelComponent } from './panels/v1-new-request-panel/v1-new-request-panel.component';
import { V1OpenRequestsPageComponent } from './pages/v1-open-requests-page/v1-open-requests-page.component';
import { V1OpenRequestsPanelComponent } from './panels/v1-open-requests-panel/v1-open-requests-panel.component';
import { V1RequestDetailPanelComponent } from './panels/v1-request-detail-panel/v1-request-detail-panel.component';
import { V1RequestableElementsPanelComponent } from './panels/v1-requestable-elements-panel/v1-requestable-elements-panel.component';

const routes: Routes = [
    { path: 'requestlist', component: V1OpenRequestsPageComponent },
    { path: 'newrequest', component: V1NewRequestPageComponent },
    { path: 'pendingjobs', component: ProductionJobListPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        NgDragDropModule.forRoot(),
        SharedModule,
        RendersModule
    ],
    declarations: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        // V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        V1NewRequestPageComponent,
        V1AvailablePartsPanelComponent,
        V1NewRequestPanelComponent,
        V1OpenRequestsPageComponent,
        V1OpenRequestsPanelComponent,
        V1RequestDetailPanelComponent,
        V1RequestableElementsPanelComponent
    ],
    exports: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        // V1MachinesPanelComponent,
        V2MachinesPanelComponent,
        V1NewRequestPageComponent,
        V1AvailablePartsPanelComponent,
        V1NewRequestPanelComponent,
        V1OpenRequestsPageComponent,
        V1OpenRequestsPanelComponent,
        V1RequestDetailPanelComponent
    ]
})
export class ProductionModule { }
