// - CORE
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgDragDropModule } from 'ng-drag-drop';
// - APPLICATION MODULES
import { RendersModule } from '../renders/renders.module';
// - COMPONENTS
import { V1DropPartPanelComponent } from './v1-drop-part-panel/v1-drop-part-panel.component';

@NgModule({
    imports: [
        CommonModule,
        NgDragDropModule.forRoot(),
        RendersModule
    ],
    declarations: [
        V1DropPartPanelComponent
    ],
    exports: [
        V1DropPartPanelComponent
    ]
})
export class AppCommonModule { }
