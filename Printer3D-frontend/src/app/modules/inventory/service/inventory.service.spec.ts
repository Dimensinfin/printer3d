// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable} from 'rxjs'
import { Router } from '@angular/router'
// - TESTING
import { inject } from '@angular/core/testing'
import { waitForAsync } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { IsolationService } from '@app/platform/isolation.service'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service'
// - DOMAIN
import { Coil } from '@domain/inventory/Coil.domain'
import { InventoryService } from './inventory.service'

describe('SERVICE InventoryService [Module: INVENTORY]', () => {
    const coil = new Coil({
        "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
        "material": "PLA",
        "color": "RED",
        "weight": 800,
        "label": "-LABEL-"
    })
    let service: InventoryService
    let httpService: SupportHttpClientWrapperService = new SupportHttpClientWrapperService()
    let isolationService: IsolationService

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: HttpClientWrapperService, useValue: httpService },
                { provide: IsolationService, useClass: SupportIsolationService }
            ]
        }).compileComponents()
        service = TestBed.inject(InventoryService)
        // isolationService = TestBed.inject(IsolationService)
    })

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(service).toBeDefined('service has not been created.')
        })
        it('Initial state', () => {
            const serviceAsAny = service as any
            expect(serviceAsAny.INVENTORYAPIV1).toBeDefined()
            expect(serviceAsAny.INVENTORYAPIV2).toBeDefined()
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [INVENTORY]', () => {
        it('apiv2_InventoryGetCoils.default: get the list of Coils', fakeAsync(() => {
            spyOn(httpService, 'wrapHttpGETCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('inventory.coils'))
                        }, 100)
                    })
                })
            service.apiv2_InventoryGetCoils()
                .subscribe((response: any) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(24)
                })
            tick(1000)
        }))
        it('apiv2_InventoryUpdateCoil.default: update a single Coil', fakeAsync(() => {
            spyOn(httpService, 'wrapHttpPATCHCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(coil)
                        }, 100)
                    })
                })
            service.apiv2_InventoryUpdateCoil(coil)
                .subscribe((response: Coil) => {
                    expect(response).toBeDefined()
                    expect(response.material).toBe("PLA")
                    expect(response.color).toBe("RED")
                    expect(response.weight).toBe(800)
                    expect(response.label).toBe('-LABEL-')
                })
            tick(1000)
        }))
    })
})
