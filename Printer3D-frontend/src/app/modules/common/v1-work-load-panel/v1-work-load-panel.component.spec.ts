import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1WorkLoadPanelComponent } from './v1-work-load-panel.component';

describe('V1WorkLoadPanelComponent', () => {
  let component: V1WorkLoadPanelComponent;
  let fixture: ComponentFixture<V1WorkLoadPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1WorkLoadPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1WorkLoadPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
