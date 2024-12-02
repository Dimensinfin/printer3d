import { TestBed } from '@angular/core/testing';

import { WSPartsAdapterService } from './wsparts-adapter.service';

describe('WSPartsAdapterService', () => {
  let service: WSPartsAdapterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WSPartsAdapterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
