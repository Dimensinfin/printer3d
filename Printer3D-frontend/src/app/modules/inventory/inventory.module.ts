// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
// - COMPONENTS
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewRollDialogComponent } from './dialogs/new-roll-dialog/new-roll-dialog.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        SharedModule
    ],
    declarations: [
        InventoryPartListPageComponent, 
        NewPartDialogComponent,
        NewRollDialogComponent
    ],
    exports: [
        InventoryPartListPageComponent, 
        NewPartDialogComponent
    ]
})
export class InventoryModule { }
