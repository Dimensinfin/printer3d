import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1AvailablePartsPanelComponent } from './v1-available-parts-panel.component';

describe('V1AvailablePartsPanelComponent', () => {
  let component: V1AvailablePartsPanelComponent;
  let fixture: ComponentFixture<V1AvailablePartsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1AvailablePartsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1AvailablePartsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
