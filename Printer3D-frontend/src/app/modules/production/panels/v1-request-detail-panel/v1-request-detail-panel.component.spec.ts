import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1RequestDetailPanelComponent } from './v1-request-detail-panel.component';

describe('V1RequestDetailPanelComponent', () => {
  let component: V1RequestDetailPanelComponent;
  let fixture: ComponentFixture<V1RequestDetailPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1RequestDetailPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1RequestDetailPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
