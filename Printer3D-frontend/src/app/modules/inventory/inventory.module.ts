import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryPartListPageComponent } from './pages/inventory-part-list-page/inventory-part-list-page.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
    declarations: [InventoryPartListPageComponent],
    imports: [
        CommonModule,
        SharedModule
    ],
    exports: [InventoryPartListPageComponent]
})
export class InventoryModule { }
