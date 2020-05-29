import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryCoilListPageComponent } from './inventory-coil-list-page.component';

describe('InventoryCoilListPageComponent', () => {
  let component: InventoryCoilListPageComponent;
  let fixture: ComponentFixture<InventoryCoilListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryCoilListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryCoilListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
