// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { InventoryCoilListPageComponent } from './inventory-coil-list-page.component';

describe('COMPONENT InventoryCoilListPageComponent [Module: INVENTORY]', () => {
    let component: InventoryCoilListPageComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                InventoryCoilListPageComponent
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(InventoryCoilListPageComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initialization flow', async () => {
            expect(component.pagePath).toBe('/Inventario/Lista Rollos');
            expect(component.columnDefs).toBeDefined();
            expect(component.columnDefs.length).toBe(0);
            expect(component.rowData.length).toBe(0);
            const componetAsAny = component as any;
            expect(componetAsAny.recordContainer).toBeDefined();
            expect(componetAsAny.backendConnections.length).toBe(0);
        });
        it('ngOnInit.after: validate initialization flow', async () => {
            await component.ngOnInit();
            expect(component.pagePath).toBe('/Inventario/Lista Rollos');
            expect(component.columnDefs).toBeDefined();
            expect(component.columnDefs.length).toBe(3, 'The column definitions number does not match');
            expect(component.rowData.length).toBe(15);
            const componetAsAny = component as any;
            expect(componetAsAny.recordContainer).toBeDefined();
            expect(componetAsAny.backendConnections.length).toBe(1);
        });
    });
    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: check the unsubscription', async () => {
            const componetAsAny = component as any;
            expect(componetAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            expect(componetAsAny.backendConnections.length).toBe(1);
            await component.ngOnDestroy();
        });
    });
});
