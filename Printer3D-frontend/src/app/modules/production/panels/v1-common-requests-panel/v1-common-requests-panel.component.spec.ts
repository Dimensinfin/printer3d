import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { V1CommonRequestsPanelComponent } from './v1-common-requests-panel.component';

xdescribe('V1CommonRequestsPanelComponent', () => {
  let component: V1CommonRequestsPanelComponent;
  let fixture: ComponentFixture<V1CommonRequestsPanelComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ V1CommonRequestsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1CommonRequestsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
