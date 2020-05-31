import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1MachinesPanelComponent } from './v1-machines-panel.component';

describe('V1MachinesPanelComponent', () => {
  let component: V1MachinesPanelComponent;
  let fixture: ComponentFixture<V1MachinesPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1MachinesPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1MachinesPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
