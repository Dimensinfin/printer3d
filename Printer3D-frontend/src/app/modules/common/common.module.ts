// - CORE
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
// - APPLICATION MODULES
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { V1DropPartPanelComponent } from './v1-drop-part-panel/v1-drop-part-panel.component';
import { V1WorkLoadPanelComponent } from './v1-work-load-panel/v1-work-load-panel.component';
import { V1BillingChartPanelComponent } from './v1-billing-chart-panel/v1-billing-chart-panel.component';
import { PatchNotesDialogComponent } from './patch-notes-dialog/patch-notes-dialog.component';
import { SharedModule } from '../shared/shared.module';
import { FeatureDockModule } from '../innovative/feature-dock/feature-dock.module';
import {DragDropModule} from '@angular/cdk/drag-drop';

@NgModule({
    imports: [
        CommonModule,
        NgxChartsModule,
        DragDropModule,
        RendersModule,
        SharedModule,
        FeatureDockModule
    ],
    declarations: [
        V1DropPartPanelComponent,
        V1WorkLoadPanelComponent,
        V1BillingChartPanelComponent,
        PatchNotesDialogComponent
    ],
    exports: [
        V1DropPartPanelComponent,
        V1WorkLoadPanelComponent,
        V1BillingChartPanelComponent,
        PatchNotesDialogComponent
    ]
})
export class AppCommonModule { }
