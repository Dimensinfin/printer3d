import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1RequestItemRenderComponent } from './v1-request-item-render.component';

describe('V1RequestItemRenderComponent', () => {
  let component: V1RequestItemRenderComponent;
  let fixture: ComponentFixture<V1RequestItemRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1RequestItemRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1RequestItemRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
