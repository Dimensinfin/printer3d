import { Component, OnDestroy, OnInit } from '@angular/core'
import { StompMessagingService } from '../stomp-adapter/stomp-messaging-service.service'

@Component({
  selector: 'app-topic-messages',
  templateUrl: './topic-messages.component.html',
  styleUrls: ['./topic-messages.component.scss'],
})
export class TopicMessagesComponent implements OnInit, OnDestroy {
  constructor(private stompService: StompMessagingService) {}
  public ngOnInit(): void {
    // this.onSendMessage()
  }
  public ngOnDestroy(): void {
    throw new Error('Method not implemented.')
  }
  public onSendMessage() {
    const message = {
      type: 'CONNECT',
      timestamp: new Date().toISOString(),
    }
    this.stompService.publish({
      destination: '/topic/demo',
      body: JSON.stringify(message),
    })
  }
}
