import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1EditPartPanelComponent } from './v1-edit-part-panel.component';

describe('V1EditPartPanelComponent', () => {
  let component: V1EditPartPanelComponent;
  let fixture: ComponentFixture<V1EditPartPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1EditPartPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1EditPartPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
