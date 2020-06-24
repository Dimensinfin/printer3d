import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1DropPartPanelComponent } from './v1-drop-part-panel.component';

describe('V1DropPartPanelComponent', () => {
  let component: V1DropPartPanelComponent;
  let fixture: ComponentFixture<V1DropPartPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1DropPartPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1DropPartPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
