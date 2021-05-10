// - CORE MODULES
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
// - COMPONENTS
import { AssetManagerService } from './AssetManager.service'

@NgModule({
    imports: [
        CommonModule
    ],
    providers: [
        { provide: AssetManagerService, useClass: AssetManagerService }
    ]
})
export class AssetManagerModule { }
