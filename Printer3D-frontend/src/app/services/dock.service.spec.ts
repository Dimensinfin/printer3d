import { TestBed } from '@angular/core/testing';

import { DockService } from './dock.service';

xdescribe('DockService', () => {
  let service: DockService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
