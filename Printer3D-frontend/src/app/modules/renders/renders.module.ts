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
// import { V1MachineRenderComponent } from './v1-machine-render/v1-machine-render.component';
// import { V2MachineRenderComponent } from './v2-machine-render/v2-machine-render.component';
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';
import { V1FeatureRenderComponent } from './v1-feature-render/v1-feature-render.component';
import { NodeContainerRenderComponent } from './node-container-render/node-container-render.component';
import { V1PartRenderComponent } from './v1-part-render/v1-part-render.component';
import { V1PartContainerRenderComponent } from './v1-part-container-render/v1-part-container-render.component';
import { V1CoilRenderComponent } from './v1-coil-render/v1-coil-render.component';
import { V2FeatureRenderComponent } from './v2-feature-render/v2-feature-render.component';
import { V1RequestRenderComponent } from './v1-request-render/v1-request-render.component';
import { V3MachineRenderComponent } from './v3-machine-render/v3-machine-render.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        NgDragDropModule.forRoot(),
    ],
    declarations: [
        V1PendingJobRenderComponent,
        // V1MachineRenderComponent,
        // V2MachineRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        V1FeatureRenderComponent,
        NodeContainerRenderComponent,
        V1PartRenderComponent,
        V1PartContainerRenderComponent,
        V1CoilRenderComponent,
        V2FeatureRenderComponent,
        V1RequestRenderComponent,
        V3MachineRenderComponent
    ],
    exports: [
        V1PendingJobRenderComponent,
        // V1MachineRenderComponent,
        // V2MachineRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        V1FeatureRenderComponent,
        NodeContainerRenderComponent,
        V1PartRenderComponent,
        V1CoilRenderComponent,
        V2FeatureRenderComponent,
        V1RequestRenderComponent,
        V3MachineRenderComponent
    ]
})
export class RendersModule { }
