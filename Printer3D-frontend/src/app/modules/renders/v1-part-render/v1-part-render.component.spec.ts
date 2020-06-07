import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PartRenderComponent } from './v1-part-render.component';

describe('V1PartRenderComponent', () => {
  let component: V1PartRenderComponent;
  let fixture: ComponentFixture<V1PartRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PartRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PartRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
