import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1PartContainerRenderComponent } from './v1-part-container-render.component';

describe('V1PartContainerRenderComponent', () => {
  let component: V1PartContainerRenderComponent;
  let fixture: ComponentFixture<V1PartContainerRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1PartContainerRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1PartContainerRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
