import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from '../shared/shared.module';
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';

@NgModule({
    declarations: [InventoryPartListPageComponent],
    imports: [
        CommonModule,
        AgGridModule.withComponents([]),
        SharedModule
    ],
    exports: [InventoryPartListPageComponent]
})
export class InventoryModule { }
