import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1CoilRenderComponent } from './v1-coil-render.component';

describe('V1CoilRenderComponent', () => {
  let component: V1CoilRenderComponent;
  let fixture: ComponentFixture<V1CoilRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1CoilRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1CoilRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
