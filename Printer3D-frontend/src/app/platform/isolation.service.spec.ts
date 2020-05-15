import { TestBed } from '@angular/core/testing';

import { IsolationService } from './isolation.service';

describe('IsolationService', () => {
  let service: IsolationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IsolationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
