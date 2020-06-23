import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1NewModelPanelComponent } from './v1-new-model-panel.component';

describe('V1NewModelPanelComponent', () => {
  let component: V1NewModelPanelComponent;
  let fixture: ComponentFixture<V1NewModelPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1NewModelPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1NewModelPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
