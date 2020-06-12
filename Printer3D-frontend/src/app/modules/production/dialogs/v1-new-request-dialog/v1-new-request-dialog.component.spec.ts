import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1NewRequestDialogComponent } from './v1-new-request-dialog.component';

describe('V1NewRequestDialogComponent', () => {
  let component: V1NewRequestDialogComponent;
  let fixture: ComponentFixture<V1NewRequestDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1NewRequestDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1NewRequestDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
