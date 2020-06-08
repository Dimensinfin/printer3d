// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../../platform/platform-constants';
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
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { V2InventoryPartListPageComponent } from './v2-inventory-part-list-page.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

describe('COMPONENT V2InventoryPartListPageComponent [Module: INVENTORY]', () => {
    let component: V2InventoryPartListPageComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V2InventoryPartListPageComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V2InventoryPartListPageComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.partContainers).toBeDefined('Check that the container list exists.');
            expect(componentAsAny.partContainers.size).toBe(0)
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.none: validate initialization flow', async () => {
            const componentAsAny = component as any;
            expect(component.getVariant()).toBe(EVariant.DEFAULT);
            expect(componentAsAny.partContainers.size).toBe(0)
            await component.ngOnInit()
            expect(component.getVariant()).toBe(EVariant.PART_LIST);
            expect(componentAsAny.partContainers).toBeDefined('Check that the container list exists.');
            expect(componentAsAny.partContainers.size).toBe(5)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(componentAsAny.dataModelRoot.length).toBe(5)
        });
    });
});
