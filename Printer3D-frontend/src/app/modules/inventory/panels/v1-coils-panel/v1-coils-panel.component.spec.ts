import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1CoilsPanelComponent } from './v1-coils-panel.component';

xdescribe('V1CoilsPanelComponent', () => {
  let component: V1CoilsPanelComponent;
  let fixture: ComponentFixture<V1CoilsPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1CoilsPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1CoilsPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
