// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { Router } from '@angular/router'
import { Observable } from 'rxjs'
// - TESTING
import { fakeAsync, tick, waitForAsync } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { routes } from '@app/testing/RouteMockUp.component'
import { Location } from "@angular/common"
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { BackendService } from '@app/services/backend.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { DockService } from '@app/modules/innovative/feature-dock/service/dock.service'
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { ProductionService } from '../../service/production.service'
// - DOMAIN
import { V1NewRequestPanelComponent } from './v1-new-request-panel.component'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { RequestItem } from '@domain/production/RequestItem.domain'
import { RequestContentType } from '@domain/interfaces/EPack.enumerated'
import { RequestForm } from '../../domain/RequestForm.domain'

describe('COMPONENT V1NewRequestPanelComponent [Module: PRODUCTION]', () => {
    let fixture: ComponentFixture<V1NewRequestPanelComponent>
    let component: V1NewRequestPanelComponent
    let isolationService: SupportIsolationService
    let location: Location
    let router: Router
    let backendService = {
        apiv2_InventoryGetParts: () => { },
        apiInventoryGetModels_v1: (provider) => { },
        apiProductionGetOpenRequests_v2: (provider) => { },
        apiNewRequest_v2: () => { }
    }
    let productionService = {
        apiv2_ProductionNewRequest: () => { }
    }
    let dockService = {
        clean: () => { }
    }

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                ReactiveFormsModule,
                FormsModule,
            ],
            declarations: [
                V1NewRequestPanelComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useValue: backendService },
                { provide: ProductionService, useValue: productionService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService },
                { provide: DockService, useValue: dockService }
            ]
        }).compileComponents()

        fixture = TestBed.createComponent(V1NewRequestPanelComponent)
        component = fixture.componentInstance
        isolationService = TestBed.inject(SupportIsolationService)
        location = TestBed.inject(Location)
        router = TestBed.inject(Router)
        router.initialNavigation()
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            expect(component.self).toBeDefined()
            expect(component.request).toBeDefined()
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getRequestDate: get the request date to be used when saving the Request', () => {
            expect(component.getRequestDate()).toBeDefined()
        })
        it('getLabel: get the label entered by the user', () => {
            const expected: string = isolationService.generateRandomString(32)
            component.request = new RequestForm({ label: expected })
            expect(component.getLabel()).toBe(expected)
        })
        it('getContentCount: get the number of elements', () => {
            component.onDrop({ dragData: new Part() })
            component.onDrop({ dragData: new Part() })
            component.onDrop({ dragData: new Part() })
            expect(component.getContentCount()).toBe(3)
        })
        it('getRequestAmount: get the amount of all elements', () => {
            component.onDrop({ dragData: new Part({ id: 'eae3112d-b16d-49ad-8a88-1d15aa0592ab', price: 10 }) })
            component.onDrop({ dragData: new Part({ id: 'eae3112d-b16d-49ad-8a88-1d15aa0592ab', price: 10 }) })
            component.onDrop({ dragData: new Part({ id: 'eae3112d-b16d-49ad-8a88-1d15aa0592ab', price: 10 }) })
            expect(component.getRequestAmount()).toBe('30€')
        })
        it('getRequestContents.Part: get the contents associated to the Request', () => {
            expect(component.getRequestContents()).toBeDefined()
            expect(component.getRequestContents().length).toBe(0)
            component.onDrop({ dragData: new Part() })
            expect(component.getRequestContents().length).toBe(1)
        })
        it('getRequestContents.Model: get the contents associated to the Request', () => {
            expect(component.getRequestContents()).toBeDefined()
            expect(component.getRequestContents().length).toBe(0)
            component.onDrop({ dragData: new Model() })
            expect(component.getRequestContents().length).toBe(1)
        })
        it('hasContents: check if the Request has contents', () => {
            expect(component.hasContents()).toBeFalse()
            component.onDrop({ dragData: new Model() })
            expect(component.hasContents()).toBeTrue()
        })
        it('isFormValid.noParts: consolidate the form status with the content container', () => {
            expect(component.isFormValid(true)).toBeFalse()
            expect(component.isFormValid(false)).toBeFalse()
        })
        it('isFormValid.parts: consolidate the form status with the content container', () => {
            component.onDrop({ dragData: new Part() })
            expect(component.isFormValid(true)).toBeTrue()
            expect(component.isFormValid(false)).toBeFalse()
        })
    })
    describe('Code Coverage Phase [Interactions]', () => {
        it('onDrop: validate the drop of elements', () => {
            expect(component.getRequestContents().length).toBe(0)
            component.onDrop({ dragData: new Part({ id: "5f27847f-2951-49fb-ae2d-2b7cc8728dd1" }) })
            expect(component.getRequestContents().length).toBe(1)
            component.onDrop({ dragData: new Model({ id: "c0d74ab6-609c-4f5f-86a3-b73b1101b7da" }) })
            expect(component.getRequestContents().length).toBe(2)
        })
        it('onDrop.invalid: validate the drop of elements', () => {
            expect(component.getRequestContents().length).toBe(0)
            component.onDrop({ dragData: new RequestItem() })
            expect(component.getRequestContents().length).toBe(0)
        })
        it('removeContent: pass the removal message to the container', () => {
            expect(component.getRequestContents().length).toBe(0)
            const testPart: Part = new Part({
                id: "5f27847f-2951-49fb-ae2d-2b7cc8728dd1"
            })
            const item: RequestItem = new RequestItem({
                itemId: "c0d74ab6-609c-4f5f-86a3-b73b1101b7da",
                type: RequestContentType.PART,
                quantity: 1,
                contents: [testPart]
            })
            // const part: Part = new Part()
            component.onDrop({ dragData: testPart })
            component.onDrop({ dragData: testPart })
            expect(component.getRequestContents().length).toBe(1)
            component.removeContent(item)
            expect(component.getRequestContents().length).toBe(1)
            component.removeContent(item)
            expect(component.getRequestContents().length).toBe(1)
        })
        it('saveRequest.success: save the Request to the backend', fakeAsync(() => {
            spyOn(productionService, 'apiv2_ProductionNewRequest').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(isolationService.directAccessTestResource('newrequest.v2'))
                        }, 100)
                    })
                })
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            component.saveRequest()
            tick(1000)
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(location.path()).toBe('/')
        }))
        it('cancelRequest: cancel the request', fakeAsync(() => {
            spyOn(dockService, 'clean')
            component.cancelRequest()
            tick(1000)
            expect(location.path()).toBe('/')
            expect(dockService.clean).toHaveBeenCalled()
        }))
    })
})
