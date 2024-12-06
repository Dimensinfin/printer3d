import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicMessagesComponent } from './topic-messages.component';

describe('TopicMessagesComponent', () => {
  let component: TopicMessagesComponent;
  let fixture: ComponentFixture<TopicMessagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopicMessagesComponent]
    });
    fixture = TestBed.createComponent(TopicMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
