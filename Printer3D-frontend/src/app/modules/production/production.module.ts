// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { ProductionJobListPageComponent } from './pages/production-job-list-page/production-job-list-page.component';
import { V1PendingJobsPanelComponent } from './panels/v1-pending-jobs-panel/v1-pending-jobs-panel.component';
import { V2MachinesPanelComponent } from './panels/v2-machines-panel/v2-machines-panel.component';
import { V1NewRequestPageComponent } from './pages/v1-new-request-page/v1-new-request-page.component';
import { V1NewRequestPanelComponent } from './panels/v1-new-request-panel/v1-new-request-panel.component';
import { V1OpenRequestsPageComponent } from './pages/v1-open-requests-page/v1-open-requests-page.component';
import { V1OpenRequestsPanelComponent } from './panels/v1-open-requests-panel/v1-open-requests-panel.component';
import { V1RequestDetailPanelComponent } from './panels/v1-request-detail-panel/v1-request-detail-panel.component';
import { V1RequestableElementsPanelComponent } from './panels/v1-requestable-elements-panel/v1-requestable-elements-panel.component';
import { DeleteConfirmationDialogComponent } from './dialogs/delete-confirmation-dialog/delete-confirmation-dialog.component';
import { V1ClosedRequestsPageComponent } from './pages/v1-closed-requests-page/v1-closed-requests-page.component';
import { V1ClosedRequestsPanelComponent } from './panels/v1-closed-requests-panel/v1-closed-requests-panel.component';
import { V1CommonRequestsPanelComponent } from './panels/v1-common-requests-panel/v1-common-requests-panel.component';
import { PayConfirmationDialogComponent } from './dialogs/pay-confirmation-dialog/pay-confirmation-dialog.component';
import { V1EditRequestPageComponent } from './pages/v1-edit-request-page/v1-edit-request-page.component';
import { V1EditRequestPanelComponent } from './panels/v1-edit-request-panel/v1-edit-request-panel.component';
import { DragDropModule } from '@angular/cdk/drag-drop';

const routes: Routes = [
    { path: 'requestlist', component: V1OpenRequestsPageComponent },
    { path: 'newrequest', component: V1NewRequestPageComponent },
    { path: 'editrequest/:requestid', component: V1EditRequestPageComponent },
    { path: 'requestlist/open', component: V1OpenRequestsPageComponent },
    { path: 'requestlist/closed', component: V1ClosedRequestsPageComponent },
    { path: 'pendingjobs', component: ProductionJobListPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        SharedModule,
        RendersModule,
        DragDropModule
    ],
    declarations: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V2MachinesPanelComponent,
        V1NewRequestPageComponent,
        V1NewRequestPanelComponent,
        V1OpenRequestsPageComponent,
        V1OpenRequestsPanelComponent,
        V1RequestDetailPanelComponent,
        V1RequestableElementsPanelComponent,
        DeleteConfirmationDialogComponent,
        V1ClosedRequestsPageComponent,
        V1ClosedRequestsPanelComponent,
        V1CommonRequestsPanelComponent,
        PayConfirmationDialogComponent,
        V1EditRequestPageComponent,
        V1EditRequestPanelComponent
    ],
    exports: [
        ProductionJobListPageComponent,
        V1PendingJobsPanelComponent,
        V2MachinesPanelComponent,
        V1NewRequestPageComponent,
        V1NewRequestPanelComponent,
        V1OpenRequestsPageComponent,
        V1OpenRequestsPanelComponent,
        V1RequestDetailPanelComponent,
        PayConfirmationDialogComponent
    ]
})
export class ProductionModule { }
