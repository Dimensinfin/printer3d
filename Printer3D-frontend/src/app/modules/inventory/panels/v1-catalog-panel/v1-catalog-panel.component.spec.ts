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
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { Request } from '@domain/Request.domain';
import { Part } from '@domain/Part.domain';
import { V1CatalogPanelComponent } from './v1-catalog-panel.component';
import { Model } from '@domain/inventory/Model.domain';

describe('COMPONENT V1CatalogPanelComponent [Module: PRODUCTION]', () => {
    let component: V1CatalogPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CatalogPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1CatalogPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(component.page).toBeUndefined()
            expect(componentAsAny.parts).toBeDefined();
            expect(componentAsAny.parts.length).toBe(0);
            expect(componentAsAny.models).toBeDefined();
            expect(componentAsAny.models.length).toBe(0);
            expect(componentAsAny.partContainers).toBeDefined();
            expect(componentAsAny.partContainers.size).toBe(0);
            expect(componentAsAny.items).toBeDefined();
            expect(componentAsAny.items.length).toBe(0);
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            jasmine.clock().tick(500);
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(2); // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(8);
            expect(componentAsAny.renderNodeList.length).toBe(8);
            expect(component.isDownloading()).toBeFalse();
            jasmine.clock().uninstall()
        });
    });
    describe('Validate Interface compliance [IViewer]', () => {
        it('fireSelectionChanged.model: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any;
            componentAsAny.page = {
                setSelected: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'setSelected')
            componentAsAny.target = new Model()
            component.fireSelectionChanged()
            expect(component.page.setSelected).toHaveBeenCalled()
        });
        it('fireSelectionChanged.any: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any;
            componentAsAny.page = {
                setSelected: (request) => {
                    expect(request).toBeDefined()
                },
                closeEditor: () => { }
            }
            spyOn(component.page, 'setSelected')
            spyOn(component.page, 'closeEditor')
            componentAsAny.target = new Request()
            component.fireSelectionChanged()
            expect(component.page.setSelected).not.toHaveBeenCalled()
            expect(component.page.closeEditor).toHaveBeenCalled()
        });
    });
    describe('Validate Interface compliance [IPartProvider]', () => {
        it('findById.found: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any;
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'NOTFOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('FOUND')).toBeDefined();
        });
        it('findById.notfound: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any;
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'FOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('NOTFOUND')).toBeUndefined()
        });
    });
});
