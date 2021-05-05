import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ExtractionsDashboardPageComponent } from './v1-extractions-dashboard-page.component';

describe('V1ExtractionsDashboardPageComponent', () => {
  let component: V1ExtractionsDashboardPageComponent;
  let fixture: ComponentFixture<V1ExtractionsDashboardPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ExtractionsDashboardPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ExtractionsDashboardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
