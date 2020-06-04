// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { NgDragDropModule } from 'ng-drag-drop';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
// - COMPONENTS
import { V1PendingJobRenderComponent } from './v1-pending-job-render/v1-pending-job-render.component';
import { V1MachineRenderComponent } from './v1-machine-render/v1-machine-render.component';
import { V2MachineRenderComponent } from './v2-machine-render/v2-machine-render.component';
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';
import { V1FeatureRenderComponent } from './v1-feature-render/v1-feature-render.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        NgDragDropModule.forRoot(),
        // SharedModule
    ],
    declarations: [
        V1PendingJobRenderComponent,
        V1MachineRenderComponent,
        V2MachineRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        V1FeatureRenderComponent
    ],
    exports: [
        V1PendingJobRenderComponent,
        V1MachineRenderComponent,
        V2MachineRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        V1FeatureRenderComponent
    ]
})
export class RendersModule { }
