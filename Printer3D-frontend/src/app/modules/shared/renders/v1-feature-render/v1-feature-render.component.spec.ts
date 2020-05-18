import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1FeatureRenderComponent } from './v1-feature-render.component';

xdescribe('V1FeatureRenderComponent', () => {
  let component: V1FeatureRenderComponent;
  let fixture: ComponentFixture<V1FeatureRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1FeatureRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1FeatureRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
