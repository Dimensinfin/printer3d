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
// - COMPONENTS
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';
import { InventoryCoilListPageComponent } from './pages/inventory-coil-list-page/inventory-coil-list-page.component';
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from './dialogs/new-coil-dialog/new-coil-dialog.component';
import { V2InventoryPartListPageComponent } from './pages/v2-inventory-part-list-page/v2-inventory-part-list-page.component';

const routes: Routes = [
    { path: 'partlist/v1', component: InventoryPartListPageComponent },
    { path: 'partlist', component: V2InventoryPartListPageComponent },
    { path: 'coillist', component: InventoryCoilListPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(routes),
        AgGridModule.withComponents([]),
        SharedModule
    ],
    declarations: [
        InventoryPartListPageComponent,
        InventoryCoilListPageComponent,
        NewPartDialogComponent,
        NewCoilDialogComponent,
        V2InventoryPartListPageComponent
    ],
    exports: [
        RouterModule,
        InventoryPartListPageComponent,
        NewPartDialogComponent, InventoryCoilListPageComponent
    ]
})
export class InventoryModule { }
