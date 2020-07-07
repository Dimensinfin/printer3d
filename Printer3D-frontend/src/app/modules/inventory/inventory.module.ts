// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgDragDropModule } from 'ng-drag-drop';
// - ROUTING
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
// - APPLICATION MODULES
import { AppCommonModule } from '../common/common.module';
import { SharedModule } from '../shared/shared.module';
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { NewPartDialogComponent } from './dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from './dialogs/new-coil-dialog/new-coil-dialog.component';
import { V2CoilListPageComponent } from './pages/v2-coil-list-page/v2-coil-list-page.component';
import { V1NewModelPageComponent } from './pages/v1-new-model-page/v1-new-model-page.component';
import { V1NewModelPanelComponent } from './panels/v1-new-model-panel/v1-new-model-panel.component';
import { V1AvailablePartsPanelComponent } from './panels/v1-available-parts-panel/v1-available-parts-panel.component';
import { V3InventoryPageComponent } from './pages/v3-inventory-page/v3-inventory-page.component';
import { V1CatalogPanelComponent } from './panels/v1-catalog-panel/v1-catalog-panel.component';
import { V1CoilsPanelComponent } from './panels/v1-coils-panel/v1-coils-panel.component';

const routes: Routes = [
    { path: 'partlist', component: V3InventoryPageComponent },
    { path: 'coillist', component: V2CoilListPageComponent },
    { path: 'newmodel', component: V1NewModelPageComponent }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        NgDragDropModule.forRoot(),
        AppCommonModule,
        SharedModule,
        RendersModule
    ],
    declarations: [
        NewPartDialogComponent,
        NewCoilDialogComponent,
        V2CoilListPageComponent,
        V1NewModelPageComponent,
        V1NewModelPanelComponent,
        V1AvailablePartsPanelComponent,
        V3InventoryPageComponent,
        V1CatalogPanelComponent,
        V1CoilsPanelComponent,
    ],
    exports: [
        RouterModule
    ]
})
export class InventoryModule { }
