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
import { V1CoilsPanelComponent } from './v1-coils-panel.component';

describe('COMPONENT V1CoilsPanelComponent [Module: INVENTORY]', () => {
    let component: V1CoilsPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CoilsPanelComponent
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1CoilsPanelComponent);
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
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(component.isDownloading()).toBeTrue();
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        });
        it('ngOnInit.after: validate initialization flow', () => {
            console.log('>[V1CoilsPanelComponent.Pre OnInit]')
            jasmine.clock().install();
            component.ngOnInit();
            jasmine.clock().tick(2100);
            console.log('>[V1CoilsPanelComponent.Post OnInit]')
            const componentAsAny = component as any;
            expect(component.getVariant()).toBe(EVariant.COIL_LIST)
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(componentAsAny.dataModelRoot.length).toBe(15);
            expect(componentAsAny.renderNodeList.length).toBe(15);
            expect(component.isDownloading()).toBeFalse();
            console.log('>[V1CoilsPanelComponent.End]')
            jasmine.clock().uninstall()
        });
    });
});
