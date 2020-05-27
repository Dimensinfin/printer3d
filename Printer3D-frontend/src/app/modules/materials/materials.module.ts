// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
// - APPLICATION MODULES
import { SharedModule } from '../shared/shared.module';
import { NewRollDialogComponent } from './dialogs/new-roll-dialog/new-roll-dialog.component';
// - COMPONENTS

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AgGridModule.withComponents([]),
        SharedModule
    ],
    declarations: [
        NewRollDialogComponent
    ],
    exports: [
    ]
})
export class MaterialsModule { }
