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
// - STORAGE
import { LOCAL_STORAGE } from 'ngx-webstorage-service';
import { SESSION_STORAGE } from 'ngx-webstorage-service';
import { StorageService } from 'ngx-webstorage-service';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { ToastrService } from 'ngx-toastr';
import { SupportLocalStorage } from '@app/testing/SupportLocalStorage.service';
import { SupportToastrService } from '@app/testing/SupportToastrService.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

describe('SERVICE IsolationService [Module: PLATFORM]', () => {
    let service: IsolationService;
    let notifier: SupportToastrService;

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
                { provide: LOCAL_STORAGE, useClass: SupportLocalStorage },
                { provide: ToastrService, useClass: SupportToastrService },
            ]
        })
            .compileComponents();
        notifier = TestBed.get(ToastrService);
        service = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            console.log('><[core/IsolationService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Storage]', async function () {
        it('setToStorage: store an object on local storage', () => {
            const TEST_DATA: string = '-TEST-DATA-TO-STORE-';
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, TEST_DATA);
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBe(TEST_DATA);
        });
        it('setToStorageObject: store a serialized object on local storage', () => {
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, new Feature());
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBe(JSON.stringify(new Feature()));
        });
        it('getFromStorage: get a serialized object from local storage', () => {
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, new Feature());
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBe(JSON.stringify(new Feature()));
        });
        it('removeFromStorage: remove an storage key', () => {
            // localStorage = new Map();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, new Feature());
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            const obtained = service.removeFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)
            expect(obtained).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeNull();
        });
    });
    describe('Code Coverage Phase [Notifications]', async function () {
        it('successNotification: show a success notification', () => {
            const TEST_MESSAGE = '-TEST_MESSAGE-';
            service.successNotification(TEST_MESSAGE);
            expect(notifier.successCount).toBe(1)
        });
        it('errorNotification: show an error notification', () => {
            const TEST_MESSAGE = '-TEST_MESSAGE-';
            service.errorNotification(TEST_MESSAGE);
            expect(notifier.errorCount).toBe(1)
        });
        it('warningNotification: show a warning notification', () => {
            const TEST_MESSAGE = '-TEST_MESSAGE-';
            service.warningNotification(TEST_MESSAGE);
            expect(notifier.warningCount).toBe(1)
        });
        it('infoNotification: show an info notification', () => {
            const TEST_MESSAGE = '-TEST_MESSAGE-';
            service.infoNotification(TEST_MESSAGE);
            expect(notifier.infoCount).toBe(1)
        });
    });
    describe('Code Coverage Phase [Utilities]', async function () {
        it('dateAdd: add time to a date instance', () => {
            expect(service.dateAdd(new Date(), 'year', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'quarter', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'month', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'week', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'day', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'hour', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'minute', 1)).toBeDefined();
            expect(service.dateAdd(new Date(), 'second', 1)).toBeDefined();
        });
    });
});
