import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1RequestableElementsPanelComponent } from './v1-requestable-elements-panel.component';

describe('V1RequestableElementsPanelComponent', () => {
  let component: V1RequestableElementsPanelComponent;
  let fixture: ComponentFixture<V1RequestableElementsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1RequestableElementsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1RequestableElementsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
