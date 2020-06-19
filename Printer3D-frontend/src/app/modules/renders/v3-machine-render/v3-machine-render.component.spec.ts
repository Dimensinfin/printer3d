import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V3MachineRenderComponent } from './v3-machine-render.component';

describe('V3MachineRenderComponent', () => {
  let component: V3MachineRenderComponent;
  let fixture: ComponentFixture<V3MachineRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V3MachineRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V3MachineRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
