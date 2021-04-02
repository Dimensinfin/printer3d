// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { EVariant, RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { V1OpenRequestsPanelComponent } from './v1-open-requests-panel.component';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { Part } from '@domain/inventory/Part.domain';
import { Model } from '@domain/inventory/Model.domain';

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
            componentAsAny.target = new CustomerRequest()
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
            component.selectRequest(new CustomerRequest())
            expect(component.page.selectRequest).toHaveBeenCalled()
        });
    });
    describe('Validate Interface compliance [IContentProvider]', () => {
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
        it('findById.model: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any;
            const modelList: Model[] = []
            modelList.push(new Model({ id: 'FOUND' }))
            modelList.push(new Model({ id: 'NOTFOUND' }))
            componentAsAny.models = modelList
            expect(component.findById('FOUND', RequestContentType.MODEL)).toBeDefined()
        });
    });
});
