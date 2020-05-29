import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InventoryPartListPageComponent } from './modules/inventory/pages/inventory-part-list-page/inventory-part-list-page.component';
import { InventoryCoilListPageComponent } from './modules/inventory/pages/inventory-coil-list-page/inventory-coil-list-page.component';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/',
        pathMatch: 'full'
    },
    { path: 'inventory/partlist', component: InventoryPartListPageComponent },
    { path: 'inventory/coillist', component: InventoryCoilListPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
