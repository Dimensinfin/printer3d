import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V3InventoryPageComponent } from './v3-inventory-page.component';

describe('V3InventoryPageComponent', () => {
  let component: V3InventoryPageComponent;
  let fixture: ComponentFixture<V3InventoryPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V3InventoryPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V3InventoryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
