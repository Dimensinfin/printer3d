import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryPartListPageComponent } from './inventory-part-list-page.component';

describe('InventoryPartListPageComponent', () => {
  let component: InventoryPartListPageComponent;
  let fixture: ComponentFixture<InventoryPartListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryPartListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryPartListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
