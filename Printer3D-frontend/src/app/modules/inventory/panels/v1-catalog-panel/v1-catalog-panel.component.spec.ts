import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1CatalogPanelComponent } from './v1-catalog-panel.component';

describe('V1CatalogPanelComponent', () => {
  let component: V1CatalogPanelComponent;
  let fixture: ComponentFixture<V1CatalogPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1CatalogPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1CatalogPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
