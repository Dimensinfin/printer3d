// - CORE MODULES
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { NgDragDropModule } from 'ng-drag-drop'
// - COMPONENTS
import { V1PendingJobRenderComponent } from './v1-pending-job-render/v1-pending-job-render.component'
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component'
import { NodeContainerRenderComponent } from './node-container-render/node-container-render.component'
import { V1PartRenderComponent } from './v1-part-render/v1-part-render.component'
import { V1PartContainerRenderComponent } from './v1-part-container-render/v1-part-container-render.component'
import { V1CoilRenderComponent } from './v1-coil-render/v1-coil-render.component'
import { V2FeatureRenderComponent } from './v2-feature-render/v2-feature-render.component'
import { V1RequestRenderComponent } from './v1-request-render/v1-request-render.component'
import { V3MachineRenderComponent } from './v3-machine-render/v3-machine-render.component'
import { V1PartStackRenderComponent } from './v1-part-stack-render/v1-part-stack-render.component'
import { V1ModelRenderComponent } from './v1-model-render/v1-model-render.component'
import { V1RequestItemRenderComponent } from './v1-request-item-render/v1-request-item-render.component'
import { V1PatchNoteRenderComponent } from './v1-patch-note-render/v1-patch-note-render.component';
import { V1ProjectRenderComponent } from './v1-project-render/v1-project-render.component'

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgDragDropModule.forRoot(),
    ],
    declarations: [
        V1PendingJobRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        NodeContainerRenderComponent,
        V1PartRenderComponent,
        V1PartContainerRenderComponent,
        V1CoilRenderComponent,
        V2FeatureRenderComponent,
        V1RequestRenderComponent,
        V3MachineRenderComponent,
        V1PartStackRenderComponent,
        V1ModelRenderComponent,
        V1RequestItemRenderComponent,
        V1PatchNoteRenderComponent,
        V1ProjectRenderComponent
    ],
    exports: [
        V1PendingJobRenderComponent,
        V1BuildCountdownTimerPanelComponent,
        NodeContainerRenderComponent,
        V1PartRenderComponent,
        V1CoilRenderComponent,
        V2FeatureRenderComponent,
        V1RequestRenderComponent,
        V3MachineRenderComponent,
        V1PartStackRenderComponent,
        V1ModelRenderComponent,
        V1RequestItemRenderComponent,
        V1PatchNoteRenderComponent,
        V1ProjectRenderComponent
    ]
})
export class RendersModule { }
