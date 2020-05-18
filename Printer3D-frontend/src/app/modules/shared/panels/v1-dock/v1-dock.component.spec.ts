import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1DockComponent } from './v1-dock.component';

xdescribe('V1DockComponent', () => {
  let component: V1DockComponent;
  let fixture: ComponentFixture<V1DockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1DockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1DockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
