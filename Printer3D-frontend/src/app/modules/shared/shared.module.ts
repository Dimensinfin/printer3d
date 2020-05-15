import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { V1PagePathPanelComponent } from './panels/v1-page-path-panel/v1-page-path-panel.component';

@NgModule({
    declarations: [V1PagePathPanelComponent],
    imports: [
        CommonModule
    ],
    exports: [V1PagePathPanelComponent]
})
export class SharedModule { }
