import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2CoilListPageComponent } from './v2-coil-list-page.component';

describe('V2CoilListPageComponent', () => {
  let component: V2CoilListPageComponent;
  let fixture: ComponentFixture<V2CoilListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2CoilListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2CoilListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
