import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRollDialogComponent } from './new-roll-dialog.component';

describe('NewRollDialogComponent', () => {
  let component: NewRollDialogComponent;
  let fixture: ComponentFixture<NewRollDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewRollDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewRollDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
