// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
// - TESTING
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { Feature } from '@domain/Feature.domain'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { V1DockComponent } from '../../common/v1-dock/v1-dock.component'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { BackendService } from '@app/services/backend.service'
import { DockService } from '@app/services/dock.service'
import { V1RequestRenderComponent } from './v1-request-render.component'
import { MatDialog } from '@angular/material/dialog'
import { SupportDockService } from '@app/testing/SupportDock.service'
import { RequestContentType, RequestState } from '@domain/interfaces/EPack.enumerated'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { RequestItem } from '@domain/production/RequestItem.domain'
import { Part } from '@domain/inventory/Part.domain'

fdescribe('COMPONENT V1RequestRenderComponent [Module: RENDERS]', () => {
    const testPart: Part = new Part({
        label: "-PART-LABEL-",
        price: 12.34,
        stockAvailable: 321
    })
    const testRequestOpen: CustomerRequest = new CustomerRequest({
        id: '8a2ac838-4ffa-4785-86bf-2f71ee1ab437',
        label: '-TEST-REQUEST-',
        requestDate: new Date(),
        state: RequestState.OPEN,
        contents: [new RequestItem({
            "itemId": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
            "type": RequestContentType.PART,
            "quantity": 5,
            "missing": 2,
            "required": 1,
            "content": testPart
        })]
    })
    const testRequestCompleted: CustomerRequest = new CustomerRequest({
        id: '8a2ac838-4ffa-4785-86bf-2f71ee1ab437',
        label: '-TEST-REQUEST-',
        requestDate: new Date(),
        state: RequestState.COMPLETED,
        contents: [new RequestItem({ quantity: 1 })]
    })
    let component: V1RequestRenderComponent
    let dockService: DockService
    let dialogRef = {
        afterClosed: () => {
            return Observable.create((observer) => {
                observer.next({})
                observer.complete()
            })
        }
    }
    let dialogFactoryService = {
        processClick: (feature: Feature) => {
            console.log('POINT')
            return dialogRef
        }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1RequestRenderComponent,
                V1DockComponent
            ],
            providers: [
                { provide: DialogFactoryService, useValue: dialogFactoryService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: MatDialog, useValue: {} },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: DockService, useClass: SupportDockService },
                // { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1RequestRenderComponent)
        component = fixture.componentInstance
        dockService = TestBed.inject(DockService)
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            expect(component.self).toBeDefined()
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getUniqueId.success: get the Request id', () => {
            component.node = testRequestOpen
            expect(component.getUniqueId()).toBe('8a2ac838-4ffa-4785-86bf-2f71ee1ab437')
        })
        it('getUniqueId.failure: get the Request id', () => {
            expect(component.getUniqueId()).toBe('-')
        })
        it('getRequestDate.success: get the Request date', () => {
            component.node = testRequestOpen
            expect(component.getRequestDate()).toBeDefined()
        })
        it('getRequestDate.failure: get the Request date', () => {
            expect(component.getRequestDate()).toBeUndefined()
        })
        it('getLabel.success: get the Request date', () => {
            component.node = testRequestOpen
            expect(component.getLabel()).toBe('-TEST-REQUEST-')
        })
        it('getLabel.failure: get the Request date', () => {
            expect(component.getLabel()).toBe('-')
        })
        it('getContentCount.success: get the numer of elements in the request', () => {
            component.node = testRequestOpen
            expect(component.getContentCount()).toBe('5')
        })
        it('getContentCount.failure: get the numer of elements in the request', () => {
            expect(component.getContentCount()).toBe('-')
        })
        it('getAmount.success: get the ', () => {
            component.node = testRequestOpen
            expect(component.getAmount()).toBe('61.7 €')
        })
        it('getAmount.failure: get the numer of elements in the request', () => {
            expect(component.getAmount()).toBe('- €')
        })
        it('isCompleted.success: the Request has parts missing', () => {
            component.node = testRequestOpen
            expect(component.isCompleted()).toBeFalse()
            component.node = testRequestCompleted
            expect(component.isCompleted()).toBeTrue()
        })
        it('isCompleted.failure: the Request has parts missing', () => {
            expect(component.isCompleted()).toBeFalse()
        })
        it('isSelected.success: get the ', () => {
            component.node = testRequestOpen
            const componentAsAny = component as any
            componentAsAny.node.selected = true
            expect(component.isSelected()).toBeTrue()
        })
        it('isSelected.failure: get the numer of elements in the request', () => {
            expect(component.isSelected()).toBeFalse()
        })
    })
})
