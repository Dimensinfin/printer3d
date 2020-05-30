// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
// - COMPONENTS
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';
import { InventoryCoilListPageComponent } from './pages/inventory-coil-list-page/inventory-coil-list-page.component';
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from './dialogs/new-coil-dialog/new-coil-dialog.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        SharedModule
    ],
    declarations: [
        InventoryPartListPageComponent, 
        InventoryCoilListPageComponent,
        NewPartDialogComponent,
        NewCoilDialogComponent
    ],
    exports: [
        InventoryPartListPageComponent, 
        NewPartDialogComponent, InventoryCoilListPageComponent
    ]
})
export class InventoryModule { }
