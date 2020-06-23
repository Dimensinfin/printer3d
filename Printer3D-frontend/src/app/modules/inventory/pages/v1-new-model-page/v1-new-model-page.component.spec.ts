import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1NewModelPageComponent } from './v1-new-model-page.component';

describe('V1NewModelPageComponent', () => {
  let component: V1NewModelPageComponent;
  let fixture: ComponentFixture<V1NewModelPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1NewModelPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1NewModelPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
