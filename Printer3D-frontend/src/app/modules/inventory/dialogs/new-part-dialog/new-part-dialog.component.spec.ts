import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPartDialogComponent } from './new-part-dialog.component';

describe('NewPartDialogComponent', () => {
  let component: NewPartDialogComponent;
  let fixture: ComponentFixture<NewPartDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPartDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPartDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
