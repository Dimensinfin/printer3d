import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2MachineRenderComponent } from './v2-machine-render.component';

describe('V2MachineRenderComponent', () => {
  let component: V2MachineRenderComponent;
  let fixture: ComponentFixture<V2MachineRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2MachineRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2MachineRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
