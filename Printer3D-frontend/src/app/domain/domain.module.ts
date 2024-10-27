import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule, Routes } from '@angular/router'
import { ContainerService } from './services/Container.service'

@NgModule({
	imports: [CommonModule],
	declarations: [],
	exports: [],
	providers: [ContainerService],
})
export class DomainModule {}
