// - CORE
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgDragDropModule } from 'ng-drag-drop';
import { NgxChartsModule }from '@swimlane/ngx-charts';
// - APPLICATION MODULES
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { V1DockComponent } from './v1-dock/v1-dock.component';
import { V1DropPartPanelComponent } from './v1-drop-part-panel/v1-drop-part-panel.component';
import { V1WorkLoadPanelComponent } from './v1-work-load-panel/v1-work-load-panel.component';
import { V1BillingChartPanelComponent } from './v1-billing-chart-panel/v1-billing-chart-panel.component';

@NgModule({
    imports: [
        CommonModule,
        NgDragDropModule.forRoot(),
        NgxChartsModule,
        RendersModule
    ],
    declarations: [
        V1DockComponent,
        V1DropPartPanelComponent,
        V1WorkLoadPanelComponent,
        V1BillingChartPanelComponent
    ],
    exports: [
        V1DockComponent,
        V1DropPartPanelComponent,
        V1WorkLoadPanelComponent,
        V1BillingChartPanelComponent
    ]
})
export class AppCommonModule { }
