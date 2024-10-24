import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { V2RequestRenderComponent } from './v2-request-render.component';

xdescribe('V2RequestRenderComponent', () => {
  let component: V2RequestRenderComponent;
  let fixture: ComponentFixture<V2RequestRenderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ V2RequestRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2RequestRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
