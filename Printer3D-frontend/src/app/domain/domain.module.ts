import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { ContainerService } from './services/Container.service'

@NgModule({
	imports: [CommonModule],
	declarations: [],
	exports: [],
	providers: [
    { provide: ContainerService, useClass: ContainerService }
  ],
})
export class DomainModule {}
