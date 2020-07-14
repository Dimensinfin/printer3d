import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1OpenRequestsPageComponent } from './v1-open-requests-page.component';

xdescribe('V1OpenRequestsPageComponent', () => {
  let component: V1OpenRequestsPageComponent;
  let fixture: ComponentFixture<V1OpenRequestsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1OpenRequestsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1OpenRequestsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
