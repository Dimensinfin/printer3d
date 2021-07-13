import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1EditRequestPanelComponent } from './v1-edit-request-panel.component';

xdescribe('V1EditRequestPanelComponent', () => {
  let component: V1EditRequestPanelComponent;
  let fixture: ComponentFixture<V1EditRequestPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1EditRequestPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1EditRequestPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
