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
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { InventoryPartListPageComponent } from './inventory-part-list-page.component';

describe('COMPONENT InventoryPartListPageComponent [Module: INVENTORY]', () => {
    let component: InventoryPartListPageComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                InventoryPartListPageComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(InventoryPartListPageComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(component.pagePath).toBe('/Inventario/Lista Piezas')
            expect(component.columnDefs).toBeDefined('Check that the column definitions are ready.');
            expect(component.columnDefs.length).toBe(0)
            expect(component.rowData).toBeDefined('Check that the grid data is empty.');
            expect(component.rowData.length).toBe(0)
            expect(componentAsAny.recordContainer).toBeDefined()
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0)
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.none: validate initialization flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0)
            await component.ngOnInit()
            expect(componentAsAny.rowData).toBeDefined('Check that the container list exists.');
            expect(componentAsAny.rowData.length).toBe(7)
            expect(componentAsAny.backendConnections.length).toBe(1)
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0, 'The initial subscription list should be 0.');
            component.ngOnInit();
            expect(componentAsAny.backendConnections.length).toBe(1, 'After initialization should be 1.');
        });
    });
});
