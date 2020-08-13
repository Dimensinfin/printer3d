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
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { V1RequestableElementsPanelComponent } from './v1-requestable-elements-panel.component';
import { Part } from '@domain/inventory/Part.domain';

describe('COMPONENT V1RequestableElementsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1RequestableElementsPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1RequestableElementsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1RequestableElementsPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.parts).toBeDefined();
            expect(componentAsAny.parts.length).toBe(0);
            expect(componentAsAny.models).toBeDefined();
            expect(componentAsAny.models.length).toBe(0);
            expect(componentAsAny.items).toBeDefined();
            expect(componentAsAny.items.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            jasmine.clock().tick(1000);
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(2);
            expect(componentAsAny.parts.length).toBe(16);
            expect(componentAsAny.models.length).toBe(2);
            expect(componentAsAny.items.length).toBe(14);
            jasmine.clock().uninstall()
        });
    });
    // - C O V E R A G E   P H A S E
    describe('Validate Interface compliance [IPartProvider]', () => {
        it('findById: serach element', () => {
            const componentAsAny = component as any;
            componentAsAny.parts = [new Part({ id: "6801b340-a572-4043-85d4-a9e10634e916" })]
            expect(component.findById("-not-found-")).toBeUndefined()
            expect(component.findById("6801b340-a572-4043-85d4-a9e10634e916")).toBeDefined()
        });
    });
});
