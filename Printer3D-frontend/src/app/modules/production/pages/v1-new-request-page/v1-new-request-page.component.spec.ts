import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1NewRequestPageComponent } from './v1-new-request-page.component';

describe('V1NewRequestPageComponent', () => {
  let component: V1NewRequestPageComponent;
  let fixture: ComponentFixture<V1NewRequestPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1NewRequestPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1NewRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
