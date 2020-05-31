import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PendingJobsPanelComponent } from './v1-pending-jobs-panel.component';

describe('V1PendingJobsPanelComponent', () => {
  let component: V1PendingJobsPanelComponent;
  let fixture: ComponentFixture<V1PendingJobsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PendingJobsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PendingJobsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
