import { Injectable } from '@angular/core'
import { RxStomp } from '@stomp/rx-stomp'
import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root',
})
export class StompMessagingService extends RxStomp {
  constructor() {
    super()
  }
}
