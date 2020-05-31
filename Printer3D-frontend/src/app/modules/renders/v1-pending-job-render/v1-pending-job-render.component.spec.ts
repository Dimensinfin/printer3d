import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PendingJobRenderComponent } from './v1-pending-job-render.component';

describe('V1PendingJobRenderComponent', () => {
  let component: V1PendingJobRenderComponent;
  let fixture: ComponentFixture<V1PendingJobRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PendingJobRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PendingJobRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
