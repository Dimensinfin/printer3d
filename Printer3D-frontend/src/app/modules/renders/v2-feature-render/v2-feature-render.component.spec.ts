import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2FeatureRenderComponent } from './v2-feature-render.component';

describe('V2FeatureRenderComponent', () => {
  let component: V2FeatureRenderComponent;
  let fixture: ComponentFixture<V2FeatureRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2FeatureRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2FeatureRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
