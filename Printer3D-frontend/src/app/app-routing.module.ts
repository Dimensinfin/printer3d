// - CORE MODULES
import { NgModule } from '@angular/core';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  { path: 'inventory', loadChildren: () => import('./modules/inventory/inventory.module').then(m => m.InventoryModule) },
  { path: 'production', loadChildren: () => import('./modules/production/production.module').then(m => m.ProductionModule) },
  { path: 'extractions', loadChildren: () => import('./modules/extraction/extraction.module').then(m => m.ExtractionModule) },
  { path: 'poc', loadChildren: () => import('./ui/inventory/inventory.module').then(m => m.InventoryContainerModule) }
]

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
