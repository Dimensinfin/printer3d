// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';
import { InventoryCoilListPageComponent } from './pages/inventory-coil-list-page/inventory-coil-list-page.component';
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from './dialogs/new-coil-dialog/new-coil-dialog.component';
import { V2PartListPageComponent } from './pages/v2-inventory-part-list-page/v2-inventory-part-list-page.component';
import { V2CoilListPageComponent } from './pages/v2-coil-list-page/v2-coil-list-page.component';
import { NewModelDialogComponent } from './dialogs/new-model-dialog/new-model-dialog.component';
import { V1ModelListPageComponent } from './pages/v1-model-list-page/v1-model-list-page.component';

const routes: Routes = [
    { path: 'partlist/v1', component: InventoryPartListPageComponent },
    { path: 'partlist', component: V2PartListPageComponent },
    { path: 'coillist/v1', component: InventoryCoilListPageComponent },
    { path: 'coillist', component: V2CoilListPageComponent },
    { path: 'modellist', component: V1ModelListPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(routes),
        AgGridModule.withComponents([]),
        SharedModule,
        RendersModule
    ],
    declarations: [
        InventoryPartListPageComponent,
        InventoryCoilListPageComponent,
        NewPartDialogComponent,
        NewCoilDialogComponent,
        V2PartListPageComponent,
        V2CoilListPageComponent,
        NewModelDialogComponent,
        V1ModelListPageComponent
    ],
    exports: [
        RouterModule,
        InventoryPartListPageComponent,
        NewPartDialogComponent, 
        V2PartListPageComponent,
        V2CoilListPageComponent,
        NewModelDialogComponent,
        V1ModelListPageComponent
    ]
})
export class InventoryModule { }
