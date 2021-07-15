import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1EditRequestPageComponent } from './v1-edit-request-page.component';

xdescribe('V1EditRequestPageComponent', () => {
  let component: V1EditRequestPageComponent;
  let fixture: ComponentFixture<V1EditRequestPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1EditRequestPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1EditRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
