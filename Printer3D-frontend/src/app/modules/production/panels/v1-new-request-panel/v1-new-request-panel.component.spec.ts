import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1NewRequestPanelComponent } from './v1-new-request-panel.component';

describe('V1NewRequestPanelComponent', () => {
  let component: V1NewRequestPanelComponent;
  let fixture: ComponentFixture<V1NewRequestPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1NewRequestPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1NewRequestPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
