import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1ExtractionComponent } from './v1-extraction.component';

describe('V1ExtractionComponent', () => {
  let component: V1ExtractionComponent;
  let fixture: ComponentFixture<V1ExtractionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1ExtractionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1ExtractionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
