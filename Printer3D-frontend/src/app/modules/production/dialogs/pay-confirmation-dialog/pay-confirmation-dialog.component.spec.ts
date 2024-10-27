import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PayConfirmationDialogComponent } from './pay-confirmation-dialog.component';

xdescribe('PayConfirmationDialogComponent', () => {
  let component: PayConfirmationDialogComponent;
  let fixture: ComponentFixture<PayConfirmationDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PayConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PayConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
