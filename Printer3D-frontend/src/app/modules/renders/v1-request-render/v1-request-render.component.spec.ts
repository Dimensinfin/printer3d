import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1RequestRenderComponent } from './v1-request-render.component';

describe('V1RequestRenderComponent', () => {
  let component: V1RequestRenderComponent;
  let fixture: ComponentFixture<V1RequestRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1RequestRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1RequestRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
