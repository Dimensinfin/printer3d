import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InventoryPartListPageComponent } from './modules/inventory/pages/inventory-part-list-page/inventory-part-list-page.component';
// import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';


const routes: Routes = [
    {
        path: '',
        redirectTo: '/',
        pathMatch: 'full'
    },
    { path: 'inventory', component: InventoryPartListPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
