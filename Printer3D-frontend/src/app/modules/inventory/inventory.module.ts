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
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from './dialogs/new-coil-dialog/new-coil-dialog.component';
import { V2PartListPageComponent } from './pages/v2-inventory-part-list-page/v2-inventory-part-list-page.component';
import { V2CoilListPageComponent } from './pages/v2-coil-list-page/v2-coil-list-page.component';
// import { NewModelDialogComponent } from './dialogs/new-model-dialog/new-model-dialog.component';
import { V1ModelListPageComponent } from './pages/v1-model-list-page/v1-model-list-page.component';
import { V1NewModelPageComponent } from './pages/v1-new-model-page/v1-new-model-page.component';
import { V1NewModelPanelComponent } from './panels/v1-new-model-panel/v1-new-model-panel.component';
import { V1AvailablePartsPanelComponent } from './panels/v1-available-parts-panel/v1-available-parts-panel.component';

const routes: Routes = [
    { path: 'partlist', component: V2PartListPageComponent },
    { path: 'coillist', component: V2CoilListPageComponent },
    { path: 'modellist', component: V1ModelListPageComponent },
    { path: 'newmodel', component: V1NewModelPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(routes),
        SharedModule,
        RendersModule
    ],
    declarations: [
        NewPartDialogComponent,
        NewCoilDialogComponent,
        V2PartListPageComponent,
        V2CoilListPageComponent,
        // NewModelDialogComponent,
        V1ModelListPageComponent,
        V1NewModelPageComponent,
        V1NewModelPanelComponent,
        V1AvailablePartsPanelComponent
    ],
    exports: [
        RouterModule,
        NewPartDialogComponent 
    ]
})
export class InventoryModule { }
