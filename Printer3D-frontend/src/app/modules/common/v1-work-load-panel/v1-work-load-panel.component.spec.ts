// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { waitForAsync } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { V1WorkLoadPanelComponent } from './v1-work-load-panel.component';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

describe('COMPONENT V1WorkLoadPanelComponent [Module: SHARED]', () => {
    let component: V1WorkLoadPanelComponent;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1WorkLoadPanelComponent
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1WorkLoadPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.workTime).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            jasmine.clock().tick(1100);
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(component.getWorkLoad()).toBe('1207h50m')
            jasmine.clock().uninstall()
        });
    });
});
