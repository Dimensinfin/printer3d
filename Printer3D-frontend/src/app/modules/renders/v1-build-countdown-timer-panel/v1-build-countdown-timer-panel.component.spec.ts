import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel.component';

describe('V1BuildCountdownTimerPanelComponent', () => {
  let component: V1BuildCountdownTimerPanelComponent;
  let fixture: ComponentFixture<V1BuildCountdownTimerPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1BuildCountdownTimerPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1BuildCountdownTimerPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
