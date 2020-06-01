import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1MachineRenderComponent } from './v1-machine-render.component';

describe('V1MachineRenderComponent', () => {
  let component: V1MachineRenderComponent;
  let fixture: ComponentFixture<V1MachineRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1MachineRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1MachineRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
