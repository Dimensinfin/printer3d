import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1RequestListPageComponent } from './v1-request-list-page.component';

describe('V1RequestListPageComponent', () => {
  let component: V1RequestListPageComponent;
  let fixture: ComponentFixture<V1RequestListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1RequestListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1RequestListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
