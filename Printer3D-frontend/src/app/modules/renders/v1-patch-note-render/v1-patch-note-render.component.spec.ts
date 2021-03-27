import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PatchNoteRenderComponent } from './v1-patch-note-render.component';

describe('V1PatchNoteRenderComponent', () => {
  let component: V1PatchNoteRenderComponent;
  let fixture: ComponentFixture<V1PatchNoteRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PatchNoteRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PatchNoteRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
