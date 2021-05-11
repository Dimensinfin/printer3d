// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - APPLICATION MODULES
// - COMPONENTS
import { V3FeatureRenderComponent } from './v3-feature-render/v3-feature-render.component';
import { DockService } from './service/dock.service';

@NgModule({
    imports: [
        CommonModule,
    ],
    declarations: [
        V3FeatureRenderComponent
    ],
    exports: [
        V3FeatureRenderComponent
        // DockService
    ],
    providers: [
        { provide: DockService, useClass: DockService }
    ],
})
export class FeatureDockModule { }
