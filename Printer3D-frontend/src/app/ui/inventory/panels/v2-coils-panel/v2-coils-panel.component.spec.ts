import { ComponentFixture, TestBed } from '@angular/core/testing';

import { V2CoilsPanelComponent } from './v2-coils-panel.component';

describe('V2CoilsPanelComponent', () => {
  let component: V2CoilsPanelComponent;
  let fixture: ComponentFixture<V2CoilsPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ V2CoilsPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(V2CoilsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
