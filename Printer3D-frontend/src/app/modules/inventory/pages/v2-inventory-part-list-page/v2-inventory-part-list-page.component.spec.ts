import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V2InventoryPartListPageComponent } from './v2-inventory-part-list-page.component';

describe('V2InventoryPartListPageComponent', () => {
  let component: V2InventoryPartListPageComponent;
  let fixture: ComponentFixture<V2InventoryPartListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V2InventoryPartListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V2InventoryPartListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
