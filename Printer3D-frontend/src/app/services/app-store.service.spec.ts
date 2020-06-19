// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../platform/platform-constants';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from './app-store.service';
import { BackendService } from './backend.service';
// import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from './httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '../testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

describe('SERVICE AppStoreService [Module: CORE]', () => {
    let service: AppStoreService;
    let isolationService: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        })
            .compileComponents();
        service = TestBed.get(AppStoreService);
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            console.log('><[core/AppStoreService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    xdescribe('Code Coverage Phase [Dock]', async () => {
        it('readDockConfiguration: get the observable to the Dock default configuration', () => {
            service.readDockConfiguration()
                .subscribe((configuration) => {
                    const obtained = configuration;
                    expect(obtained).toBeDefined();
                    expect(obtained.length).toBe(2);
                })
        });
    });
    describe('Code Coverage Phase [Global Support Methods]', () => {
        it('isEmpty.success: check if a variable has contents', () => {
            console.log('><[Global Support Methods]> isEmpty.success: check if a variable has contents');
            expect(service.isEmpty()).toBeTruthy();
            expect(service.isEmpty(null)).toBeTruthy();
            expect(service.isEmpty('')).toBeTruthy();
            expect(service.isEmpty("")).toBeTruthy();
            expect(service.isEmpty([])).toBeTruthy();
            expect(service.isEmpty(" ")).toBeFalsy();
        });
        it('accessProperties.success: access a properties file', () => {
            console.log('><[Global Support Methods]> accessProperties.success: access a properties file');
            expect(service.accessProperty('tab-definitions')).toBeDefined();
        });
    });
});
