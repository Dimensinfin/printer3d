import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ClosedRequestsPanelComponent } from './v1-closed-requests-panel.component';

describe('V1ClosedRequestsPanelComponent', () => {
  let component: V1ClosedRequestsPanelComponent;
  let fixture: ComponentFixture<V1ClosedRequestsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ClosedRequestsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ClosedRequestsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
