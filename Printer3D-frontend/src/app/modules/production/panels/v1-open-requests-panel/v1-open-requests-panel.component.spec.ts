import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1OpenRequestsPanelComponent } from './v1-open-requests-panel.component';

describe('V1OpenRequestsPanelComponent', () => {
  let component: V1OpenRequestsPanelComponent;
  let fixture: ComponentFixture<V1OpenRequestsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1OpenRequestsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1OpenRequestsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
