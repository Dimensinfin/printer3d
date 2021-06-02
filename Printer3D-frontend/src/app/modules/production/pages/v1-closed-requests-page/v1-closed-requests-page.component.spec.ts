import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ClosedRequestsPageComponent } from './v1-closed-requests-page.component';

xdescribe('V1ClosedRequestsPageComponent', () => {
  let component: V1ClosedRequestsPageComponent;
  let fixture: ComponentFixture<V1ClosedRequestsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ClosedRequestsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ClosedRequestsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
