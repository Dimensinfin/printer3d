// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
// - COMPONENTS
import { V1PendingJobRenderComponent } from './v1-pending-job-render/v1-pending-job-render.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        SharedModule
    ],
    declarations: [V1PendingJobRenderComponent],
    exports: [V1PendingJobRenderComponent]
})
export class RendersModule { }
