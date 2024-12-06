import { Component, OnDestroy, OnInit } from '@angular/core'
import { StompMessagingService } from '../stomp-adapter/stomp-messaging-service.service'
import { Message } from '@stomp/stompjs'
import { Subscription } from 'rxjs'

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.scss'],
})
export class LandingpageComponent {
  public connected: boolean = false
  public receivedMessages: string[] = []
  private topicSubscription!: Subscription

  constructor(private stompService: StompMessagingService) {
    // console.log('Connecting to websockets server...')
  }

  // ngOnInit() {
  //   console.log("Connecting to topic...")
  //   this.topicSubscription = this.stompService
  //     .watch('/topic/part')
  //     .subscribe((message: Message) => {
  //       console.log("Received message for topic parts")
  //       this.receivedMessages.push(message.body)
  //     })
  // }
  // ngOnDestroy() {
  //   console.log("Disconnect from topic...")
  //  this.topicSubscription.unsubscribe()
  // }

  // sendMessage() {
  //   // this.stompService.send('/topic/your-topic', JSON.stringify({ text: 'Hello, STOMP!' }))
  // }
}
