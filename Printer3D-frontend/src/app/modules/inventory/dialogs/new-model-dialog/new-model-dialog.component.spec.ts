import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewModelDialogComponent } from './new-model-dialog.component';

describe('NewModelDialogComponent', () => {
  let component: NewModelDialogComponent;
  let fixture: ComponentFixture<NewModelDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewModelDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewModelDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
