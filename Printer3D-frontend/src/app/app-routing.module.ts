// - CORE MODULES
import { NgModule } from '@angular/core';
// - BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
// - APPLICATION MODULES

const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  { path: 'inventory', loadChildren: () => import('./modules/inventory/inventory.module').then(m => m.InventoryModule) },
  { path: 'production', loadChildren: () => import('./modules/production/production.module').then(m => m.ProductionModule) }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
