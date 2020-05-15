import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PagePathPanelComponent } from './v1-page-path-panel.component';

describe('V1PagePathPanelComponent', () => {
  let component: V1PagePathPanelComponent;
  let fixture: ComponentFixture<V1PagePathPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PagePathPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PagePathPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
