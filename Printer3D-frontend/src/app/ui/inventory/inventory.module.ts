import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { V2CoilsPanelComponent } from './panels/v2-coils-panel/v2-coils-panel.component';
import { V3CoilsPageComponent } from './pages/v3-coils-page/v3-coils-page.component';

const routes: Routes = [
  { path: 'coilsAuto', component: V3CoilsPageComponent },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
  ],
  declarations: [
    V2CoilsPanelComponent,
    V3CoilsPageComponent
  ],
  exports: [
    RouterModule,
    V2CoilsPanelComponent,
    V3CoilsPageComponent
],
})
export class InventoryContainerModule { }
