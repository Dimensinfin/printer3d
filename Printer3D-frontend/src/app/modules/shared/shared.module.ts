// - CORE
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - COMPONENTS
// import { V1PagePathPanelComponent } from './panels/v1-page-path-panel/v1-page-path-panel.component';
// import { V1DockComponent } from '../common/v1-dock/v1-dock.component';
// import { V1FeatureRenderComponent } from '../renders/v1-feature-render/v1-feature-render.component';
import { BackgroundEnabledComponent } from './core/background-enabled/background-enabled.component';
import { RendersModule } from '../renders/renders.module';
import { ViewerPanelComponent } from './core/viewer-panel/viewer-panel.component';
import { AppPanelComponent } from './core/app-panel/app-panel.component';

@NgModule({
    imports: [
        CommonModule,
        RendersModule
    ],
    declarations: [
        // V1PagePathPanelComponent,
        // V1DockComponent,
        // V1FeatureRenderComponent,
        BackgroundEnabledComponent,
        AppPanelComponent,
        ViewerPanelComponent
    ],
    exports: [
        // V1PagePathPanelComponent,
        // V1DockComponent,
        // V1FeatureRenderComponent,
        BackgroundEnabledComponent,
        AppPanelComponent,
        ViewerPanelComponent
    ]
})
export class SharedModule { }
