import { TestBed } from '@angular/core/testing';

import { DialogFactoryService } from './dialog-factory.service';

describe('DialogFactoryService', () => {
  let service: DialogFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DialogFactoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
