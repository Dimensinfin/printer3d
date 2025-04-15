// - CORE MODULES
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
// - COMPONENTS
import { SupportIsolationService } from './SupportIsolation.service'

@NgModule({
    imports: [
        CommonModule
    ], 
    providers: [
        // - SERVICES
        { provide: SupportIsolationService, useClass: SupportIsolationService }
    ]
})
export class TestingSupportModule { }
