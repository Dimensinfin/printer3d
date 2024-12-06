import { Component, OnDestroy, OnInit } from '@angular/core'
import { StompMessagingService } from '../stomp-adapter/stomp-messaging-service.service'
import { Client, Message } from '@stomp/stompjs'
import { Part } from './Part.domain'

@Component({
  selector: 'app-topic-messages',
  templateUrl: './topic-messages.component.html',
  styleUrls: ['./topic-messages.component.scss'],
})
export class TopicMessagesComponent implements OnInit {
  public message: string
  public parts: Part[]=[]
  private client: Client

  constructor() {
    this.client = new Client()
    this.message = ''

    this.client.configure({
      brokerURL: 'ws://localhost:9052/printer-sockets/websocket',
      onConnect: () => {
        this.client.subscribe('/topic/parts', (message: Message) => {
          this.message = message.body
          console.log('Received message:', this.message)
          const newPart = new Part(JSON.parse(this.message))
          this.parts.push(newPart)
        })
      },
      onChangeState(state) {
        console.log('State Changed to: ', state) // 0=active, 1=deactivating,2=inactive
      },
      onStompError(frame) {
        console.log('Stomp Error Ocurred with frame: ', frame.body)
      },
      onUnhandledFrame(frame) {
        console.log('Unhandled frame: ', frame.body)
      },
      onUnhandledReceipt(frame) {
        console.log('Unhandled Recept: ', frame.body)
      },
      onUnhandledMessage(frame) {
        console.log('Unhandled Message: ', frame.body)
      },
      onWebSocketClose() {
        'Web Socket Closed'
      },
      onWebSocketError() {
        'Web Socket Error Occurred'
      },
      onDisconnect(frame) {
        console.log('Disconnected :', frame.body)
      },
    })
  }

  ngOnInit(): void {
    console.log('WebSocketImplComponent ngOnInit() called')
    this.client.activate()
  }
}
