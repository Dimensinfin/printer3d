import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PartStackRenderComponent } from './v1-part-stack-render.component';

describe('V1PartStackRenderComponent', () => {
  let component: V1PartStackRenderComponent;
  let fixture: ComponentFixture<V1PartStackRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PartStackRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PartStackRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
