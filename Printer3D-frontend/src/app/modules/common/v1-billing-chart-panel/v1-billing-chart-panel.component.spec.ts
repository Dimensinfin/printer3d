import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1BillingChartPanelComponent } from './v1-billing-chart-panel.component';

describe('V1BillingChartPanelComponent', () => {
  let component: V1BillingChartPanelComponent;
  let fixture: ComponentFixture<V1BillingChartPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1BillingChartPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1BillingChartPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
