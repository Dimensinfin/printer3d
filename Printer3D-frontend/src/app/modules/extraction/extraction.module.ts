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
import { V1ExtractionsDashboardPageComponent } from './pages/v1-extractions-dashboard-page/v1-extractions-dashboard-page.component';
import { V1ExtractionComponent } from './components/v1-extraction/v1-extraction.component';

const routes: Routes = [
    { path: '', component: V1ExtractionsDashboardPageComponent },
]
@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        RendersModule
    ],
    declarations: [
        V1ExtractionsDashboardPageComponent,
        V1ExtractionComponent
    ],
    exports: [
        V1ExtractionsDashboardPageComponent
    ]
})
export class ExtractionModule { }
