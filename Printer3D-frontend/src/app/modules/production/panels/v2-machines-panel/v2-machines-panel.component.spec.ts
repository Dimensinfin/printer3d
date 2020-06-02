import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2MachinesPanelComponent } from './v2-machines-panel.component';

describe('V2MachinesPanelComponent', () => {
  let component: V2MachinesPanelComponent;
  let fixture: ComponentFixture<V2MachinesPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2MachinesPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2MachinesPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
