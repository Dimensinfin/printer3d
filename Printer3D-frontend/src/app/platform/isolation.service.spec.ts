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
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { SelectableService } from 'ag-grid-community';

describe('SERVICE IsolationService [Module: PLATFORM]', () => {
    let service: IsolationService;
    let localStorage = new Map();

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
                { provide: LOCAL_STORAGE, useValue: localStorage },
            ]
        })
            .compileComponents();
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
        it('setToStorageObject: store a serialized object on local storage', () => {
            localStorage = new Map();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, new Feature());
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBe(JSON.stringify(new Feature()));
        });
        it('getFromStorage: get a serialized object from local storage', () => {
            localStorage = new Map();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeUndefined();
            service.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, new Feature());
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBeDefined();
            expect(service.getFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)).toBe(JSON.stringify(new Feature()));
        });
    });
});
