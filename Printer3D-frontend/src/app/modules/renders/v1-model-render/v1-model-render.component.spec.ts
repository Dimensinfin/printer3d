import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ModelRenderComponent } from './v1-model-render.component';

describe('V1ModelRenderComponent', () => {
  let component: V1ModelRenderComponent;
  let fixture: ComponentFixture<V1ModelRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ModelRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ModelRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
