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
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { EVariant, RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { V1OpenRequestsPanelComponent } from './v1-open-requests-panel.component';
import { Request } from '@domain/Request.domain';
import { Part } from '@domain/Part.domain';

describe('COMPONENT V1OpenRequestsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1OpenRequestsPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1OpenRequestsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1OpenRequestsPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(component.page).toBeUndefined()
            expect(componentAsAny.parts).toBeDefined();
            expect(componentAsAny.parts.length).toBe(0);
            expect(componentAsAny.models).toBeDefined();
            expect(componentAsAny.models.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase',  () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            jasmine.clock().tick(1200);
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(3); // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(2);
            expect(componentAsAny.renderNodeList.length).toBe(2);
            expect(component.isDownloading()).toBeFalse();
            jasmine.clock().uninstall()
        });
    });
    describe('Validate Interface compliance [IViewer]', () => {
        it('fireSelectionChanged: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any;
            componentAsAny.page = {
                selectRequest: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'selectRequest')
            componentAsAny.target = new Request()
            component.fireSelectionChanged()
            expect(component.page.selectRequest).toHaveBeenCalled()
        });
        it('selectRequest: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any;
            componentAsAny.page = {
                selectRequest: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'selectRequest')
            component.selectRequest(new Request())
            expect(component.page.selectRequest).toHaveBeenCalled()
        });
    });
    describe('Validate Interface compliance [IPartProvider]', () => {
        it('findById.found: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any;
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'NOTFOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('FOUND', RequestContentType.PART)).toBeDefined();
        });
        it('findById.notfound: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any;
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'FOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('NOTFOUND', RequestContentType.PART)).toBeUndefined()
        });
    });
});
