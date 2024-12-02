import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'
import { StompMessagingService } from './stomp-messaging-service.service'
import { StompServiceFactory } from './stomp-service-factory'

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [
    {
      provide: StompMessagingService,
      useFactory: StompServiceFactory,
    },
  ],
})
export class StompModule {}
