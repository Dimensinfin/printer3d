// - CORE MODULES
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
// - APPLICATION MODULES
// - COMPONENTS
import { V3FeatureRenderComponent } from './v3-feature-render/v3-feature-render.component'
import { DockService } from './service/dock.service'
import { V1DockComponent } from './components/v1-dock/v1-dock.component'

@NgModule({
    imports: [
        CommonModule,
    ],
    declarations: [
        V3FeatureRenderComponent,
        V1DockComponent
    ],
    exports: [
        V3FeatureRenderComponent,
        V1DockComponent
    ],
    providers: [
        { provide: DockService, useClass: DockService }
    ],
})
export class FeatureDockModule { }
