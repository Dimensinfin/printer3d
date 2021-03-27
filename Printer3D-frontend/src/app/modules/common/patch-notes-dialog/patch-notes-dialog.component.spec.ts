import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatchNotesDialogComponent } from './patch-notes-dialog.component';

xdescribe('PatchNotesDialogComponent', () => {
  let component: PatchNotesDialogComponent;
  let fixture: ComponentFixture<PatchNotesDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatchNotesDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatchNotesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
