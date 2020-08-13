// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - HTTP PACKAGE
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpTestingController } from '@angular/common/http/testing';
// - TESTING
import { async } from '@angular/core/testing';
import { inject } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { HttpClientWrapperService } from './httpclientwrapper.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Part } from '@domain/inventory/Part.domain';

xdescribe('SERVICE HttpClientWrapperService [Module: APP]', () => {
    let service: HttpClientWrapperService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                HttpClientTestingModule
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                {
                    provide: HttpClient, useValue: {
                        get: (request: string, headers?: object) => {
                            return Observable.create((observer) => {
                                observer.next({});
                                observer.complete();
                            });
                        },
                        post: (request: string, body?: string, headers?: object) => {
                            return Observable.create((observer) => {
                                observer.next({});
                                observer.complete();
                            });
                        },
                        put: (request: string, headers?: object) => {
                            return Observable.create((observer) => {
                                observer.next({});
                                observer.complete();
                            });
                        },
                        patch: (request: string, body?: string, headers?: object) => {
                            return Observable.create((observer) => {
                                observer.next({});
                                observer.complete();
                            });
                        }
                    }
                },
            ]
        })
            .compileComponents();
        service = TestBed.get(HttpClientWrapperService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[app/HttpClientWrapperService]> should be created');
            expect(service).toBeTruthy('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Http Wrappers]', () => {
        it('wrapHttpGETCall.success: make a GET request', async () => {
            service.wrapHttpGETCall('request')
                .subscribe((response: any) => {
                    expect(response).toBeDefined();
                });
        });
        it('wrapHttpPUTCall.success: make a PUT request', async () => {
            service.wrapHttpPUTCall('request')
                .subscribe((response: any) => {
                    expect(response).toBeDefined();
                });
        });
        it('wrapHttpPATCHCall.success: make a PATCH request', async () => {
            await service.wrapHttpPATCHCall('request', '-DATA-')
                .subscribe((response: any) => {
                    expect(response).toBeDefined();
                });
        });
        it('wrapHttpRESOURCECall.success: make a RESOURCE request', async () => {
            service.wrapHttpRESOURCECall('request')
                .subscribe((response: any) => {
                    expect(response).toBeDefined();
                });
        });
        it('wrapHttpPOSTCall.success: make a POST request', () => {
            service.wrapHttpPOSTCall('request', '-DATA-')
                .subscribe((response: any) => {
                    expect(response).toBeDefined();
                });
        });
        it('wrapHttpSecureHeaders.empty: make a GET request', () => {
            const serviceAsAny = service as any;
            expect(serviceAsAny.wrapHttpSecureHeaders(new HttpHeaders())).toBeDefined();
        });
        it('wrapHttpSecureHeaders.headers: make a GET request', () => {
            const serviceAsAny = service as any;
            const headers = new HttpHeaders()
                .set('Content-Type', 'application/json; charset=utf-8')
                .set('xApp-Name', 'name');
            expect(serviceAsAny.wrapHttpSecureHeaders(headers)).toBeDefined();
        });
    });
});
