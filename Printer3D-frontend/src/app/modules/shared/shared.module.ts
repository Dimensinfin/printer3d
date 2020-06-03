// - CORE
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - COMPONENTS
import { V1PagePathPanelComponent } from './panels/v1-page-path-panel/v1-page-path-panel.component';
import { V1DockComponent } from './panels/v1-dock/v1-dock.component';
import { V1FeatureRenderComponent } from './renders/v1-feature-render/v1-feature-render.component';
import { BackgroundEnabledComponent } from './core/background-enabled/background-enabled.component';

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        V1PagePathPanelComponent,
        V1DockComponent,
        V1FeatureRenderComponent,
        BackgroundEnabledComponent,
    ],
    exports: [
        V1PagePathPanelComponent,
        V1DockComponent,
        V1FeatureRenderComponent,
        BackgroundEnabledComponent,
    ]
})
export class SharedModule { }
