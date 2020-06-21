import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1Part4RequestRenderComponent } from './v1-part4-request-render.component';

describe('V1Part4RequestRenderComponent', () => {
  let component: V1Part4RequestRenderComponent;
  let fixture: ComponentFixture<V1Part4RequestRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1Part4RequestRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1Part4RequestRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
