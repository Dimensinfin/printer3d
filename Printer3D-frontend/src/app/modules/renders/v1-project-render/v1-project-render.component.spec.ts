import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ProjectRenderComponent } from './v1-project-render.component';

describe('V1ProjectRenderComponent', () => {
  let component: V1ProjectRenderComponent;
  let fixture: ComponentFixture<V1ProjectRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ProjectRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ProjectRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
