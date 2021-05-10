// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { V1PendingJobsPanelComponent } from './v1-pending-jobs-panel.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

describe('COMPONENT V1PendingJobsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1PendingJobsPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1PendingJobsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1PendingJobsPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(component.active).toBeFalse();
            expect(component.jobs).toBeDefined();
            expect(component.jobs.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            jasmine.clock().install();
            await component.ngOnInit();
            jasmine.clock().tick(1100);
            const componentAsAny = component as any;
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(componentAsAny.dataModelRoot.length).toBe(43);
            expect(componentAsAny.renderNodeList.length).toBe(43);
            expect(component.isDownloading()).toBeFalse();
            jasmine.clock().uninstall()
        });
    });
});
