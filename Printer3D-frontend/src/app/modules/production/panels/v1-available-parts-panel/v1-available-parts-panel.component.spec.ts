// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/app-store.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { V1AvailablePartsPanelComponent } from './v1-available-parts-panel.component';

describe('COMPONENT V1AvailablePartsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1AvailablePartsPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1AvailablePartsPanelComponent
            ],
            providers: [
                // { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService },
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1AvailablePartsPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            // expect(componentAsAny.partContainers).toBeDefined()
            // expect(componentAsAny.partContainers.size).toBe(0)
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initialization flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(component.isDownloading()).toBeTrue();
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        });
        it('ngOnInit.after: validate initialization flow', async () => {
            console.log('>[V1AvailablePartsPanelComponent.Pre OnInit]')
            jasmine.clock().install();
            await component.ngOnInit();
            jasmine.clock().tick(1100);
            console.log('>[V1AvailablePartsPanelComponent.Post OnInit]')
            const componentAsAny = component as any;
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(componentAsAny.dataModelRoot.length).toBe(16);
            expect(componentAsAny.renderNodeList.length).toBe(16);
            expect(component.isDownloading()).toBeFalse();
            jasmine.clock().uninstall()
        });
    });
    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: check the unsubscription', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            expect(componentAsAny.backendConnections.length).toBe(1);
            await component.ngOnDestroy();
        });
    });
});
