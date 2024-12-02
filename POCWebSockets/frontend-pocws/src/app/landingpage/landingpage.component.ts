import { Component } from '@angular/core'
import { StompMessagingService } from '../stomp-adapter/stomp-messaging-service.service'

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.scss'],
})
export class LandingpageComponent {
  public connected: boolean = false

  constructor(private stompService: StompMessagingService) {
    console.log('Connecting to websockets server...')
  }

  ngOnInit() {
    this.stompService.subscribe('/topic/parts').subscribe((message) => {
      console.log('Received message:', message)
    })
  }

  sendMessage() {
    // this.stompService.send('/topic/your-topic', JSON.stringify({ text: 'Hello, STOMP!' }))
  }
}
