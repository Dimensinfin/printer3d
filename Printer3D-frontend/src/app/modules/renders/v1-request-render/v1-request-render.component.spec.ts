// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
import { Router } from '@angular/router';
// - TESTING
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { routes } from '@app/testing/RouteMockUp.component'
import { Location } from "@angular/common";
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { Feature } from '@bit/innovative.innovative-core.feature-dock/domain/Feature.domain'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
// import { V1DockComponent } from '../../common/v1-dock/v1-dock.component'
import { BackendService } from '@app/services/backend.service'
import { DockService } from '@app/modules/innovative/feature-dock/service/dock.service'
import { V1RequestRenderComponent } from './v1-request-render.component'
import { MatDialog } from '@angular/material/dialog'
import { RequestContentType, RequestState } from '@domain/interfaces/EPack.enumerated'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { RequestItem } from '@domain/production/RequestItem.domain'
import { Part } from '@domain/inventory/Part.domain'

describe('COMPONENT V1RequestRenderComponent [Module: RENDERS]', () => {
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
    let backendService = { apiProductionRequestsClose_v2: () => { } }
    let dockService = { clean: () => { } }
    let matDialog = { open: () => { } }
    let location: Location;
    let router: Router;

    let dialogRef = {
        afterClosed: () => {
            return new Observable((observer) => {
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
                // V1DockComponent
            ],
            providers: [
                { provide: DialogFactoryService, useValue: dialogFactoryService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: MatDialog, useValue: matDialog },
                { provide: BackendService, useValue: backendService },
                { provide: DockService, useValue: dockService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1RequestRenderComponent)
        component = fixture.componentInstance
        location = TestBed.inject(Location)
        router = TestBed.inject(Router)
        router.initialNavigation()
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
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
    describe('Code Coverage Phase [Interactions]', () => {
        it('getContents.success: get the Request contents', () => {
            component.node = testRequestOpen
            expect(component.getContents()).toBeDefined()
            expect(component.getContents().length).toBe(1)
        })
        it('getContents.failure: get the Request contents', () => {
            expect(component.getContents()).toBeDefined()
            expect(component.getContents().length).toBe(0)
        })
        it('selectRequest.success: mark seft as selected', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                addSelection: () => { },
                subtractSelection: () => { },
            }
            component.node = testRequestOpen
            component.node = testRequestOpen
            componentAsAny.node.selected = false
            expect(component.isSelected()).toBeFalse()
            component.selectRequest()
            expect(component.isSelected()).toBeTrue()
        })
        it('selectRequest.failure: mark seft as selected', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                addSelection: () => { },
                subtractSelection: () => { },
            }
            expect(component.isSelected()).toBeFalse()
            component.selectRequest()
            expect(component.isSelected()).toBeFalse()
        })
        it('completeRequest.success: complete a request and collect the amount', fakeAsync(() => {
            // Given
            const componentAsAny = component as any
            componentAsAny.container = {
                addSelection: () => { },
                subtractSelection: () => { },
            }
            component.node = testRequestCompleted
            spyOn(backendService, 'apiProductionRequestsClose_v2').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(testRequestCompleted)
                        }, 100)
                    })
                })
            spyOn(dockService, 'clean')
            // Test
            component.completeRequest()
            tick(1000)
            // Assertions
            expect(dockService.clean).toHaveBeenCalled()
            expect(location.path()).toBe('/');
        }))
        it('deleteRequest.success: delete the customer request from the list', fakeAsync(() => {
            // Given
            const componentAsAny = component as any
            componentAsAny.container = {
                addSelection: () => { },
                subtractSelection: () => { },
            }
            component.node = testRequestCompleted
            spyOn(matDialog, 'open').and
                .callFake(function () {
                    console.log('step11')
                    return {
                        test: true,
                        afterClosed: () => {
                            return new Observable(observer => {
                                setTimeout(() => {
                                    observer.next('DELETED')
                                    observer.complete()
                                }, 100)
                            })
                        }
                    }
                })
            // Test
            console.log('step01')
            component.deleteRequest()
            console.log('step02')
            expect(matDialog.open).toHaveBeenCalled()
            console.log('step03')
            tick(1000)
            // Assertions
            expect(location.path()).toBe('/production/requestlist');
        }))
        it('deleteRequest.cancel: delete the customer request from the list', fakeAsync(() => {
            // Given
            spyOn(matDialog, 'open').and
                .callFake(function () {
                    console.log('step11')
                    return {
                        test: true,
                        afterClosed: () => {
                            return new Observable(observer => {
                                setTimeout(() => {
                                    observer.next('CANCEL')
                                    observer.complete()
                                }, 100)
                            })
                        }
                    }
                })
            // Test
            console.log('step01')
            component.deleteRequest()
            console.log('step02')
            expect(matDialog.open).toHaveBeenCalled()
            console.log('step03')
            tick(1000)
            // Assertions
            expect(location.path()).toBe('/');
        }))
    })
})
