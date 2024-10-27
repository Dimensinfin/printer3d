import { ComponentFixture, TestBed } from '@angular/core/testing';

import { V3CoilsPageComponent } from './v3-coils-page.component';

describe('V3CoilsPageComponent', () => {
  let component: V3CoilsPageComponent;
  let fixture: ComponentFixture<V3CoilsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ V3CoilsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(V3CoilsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
