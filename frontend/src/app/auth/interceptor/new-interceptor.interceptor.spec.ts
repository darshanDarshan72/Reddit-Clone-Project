import { TestBed } from '@angular/core/testing';

import { NewInterceptorInterceptor } from './new-interceptor.interceptor';

describe('NewInterceptorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      NewInterceptorInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: NewInterceptorInterceptor = TestBed.inject(NewInterceptorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
