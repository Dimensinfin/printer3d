// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { ProductionJobListPageComponent } from './pages/production-job-list-page/production-job-list-page.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        SharedModule,
        RendersModule
    ],
    declarations: [
        ProductionJobListPageComponent
    ],
    exports: [
        ProductionJobListPageComponent
    ]
})
export class ProductionModule { }
