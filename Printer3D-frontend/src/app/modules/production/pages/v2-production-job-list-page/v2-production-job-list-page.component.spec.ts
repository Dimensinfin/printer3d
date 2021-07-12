import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2ProductionJobListPageComponent } from './v2-production-job-list-page.component';

describe('V2ProductionJobListPageComponent', () => {
  let component: V2ProductionJobListPageComponent;
  let fixture: ComponentFixture<V2ProductionJobListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2ProductionJobListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2ProductionJobListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
