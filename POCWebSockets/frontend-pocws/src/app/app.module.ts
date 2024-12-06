import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { StompMessagingService } from './stomp-adapter/stomp-messaging-service.service';
import { StompServiceFactory } from './stomp-adapter/stomp-service-factory';
import { TopicMessagesComponent } from './topic-messages/topic-messages.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingpageComponent,
    TopicMessagesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [ {
    provide: StompMessagingService,  useFactory: StompServiceFactory,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
