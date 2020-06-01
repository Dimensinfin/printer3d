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

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        NgDragDropModule.forRoot(),
        SharedModule
    ],
    declarations: [
        V1PendingJobRenderComponent,
        V1MachineRenderComponent
    ],
    exports: [
        V1PendingJobRenderComponent,
        V1MachineRenderComponent
    ]
})
export class RendersModule { }
