import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductionJobListPageComponent } from './production-job-list-page.component';

xdescribe('ProductionJobListPageComponent', () => {
  let component: ProductionJobListPageComponent;
  let fixture: ComponentFixture<ProductionJobListPageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductionJobListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionJobListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
