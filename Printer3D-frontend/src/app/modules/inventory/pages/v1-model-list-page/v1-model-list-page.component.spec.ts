import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ModelListPageComponent } from './v1-model-list-page.component';

describe('V1ModelListPageComponent', () => {
  let component: V1ModelListPageComponent;
  let fixture: ComponentFixture<V1ModelListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ModelListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ModelListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
