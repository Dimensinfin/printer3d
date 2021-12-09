// - CORE
import { NO_ERRORS_SCHEMA, ɵbypassSanitizationTrustStyle } from '@angular/core'
import { Router } from '@angular/router'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
import { Location } from "@angular/common"
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { Feature } from '@bit/innovative.innovative-core.feature-dock/domain/Feature.domain'
import { DockService } from './dock.service'
import { HttpClientWrapperService } from '../../../../services/httpclientwrapper.service'
import { Observable } from 'rxjs'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'

describe('service DockService [Module: SERVICES]', () => {
    let service: DockService
    let routerDetector: any = { refresh: () => { } }
    let location: Location
    let router: Router
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let clientWrapperService = {
        wrapHttpRESOURCECall: () => {
            console.log('DockService->spyOn.wrapHttpRESOURCECall')
            return new Observable(observer => {
                setTimeout(() => {
                    observer.next(isolationService.directAccessAssetResource('properties/config/DockFeatureMap'))
                }, 100)
            })
        }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
            ],
            declarations: [
            ],
            providers: [
                { provide: HttpClientWrapperService, useValue: clientWrapperService }
            ]
        }).compileComponents()

        service = TestBed.inject(DockService)
        location = TestBed.inject(Location)
        router = TestBed.inject(Router)
        router.initialNavigation()
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(service).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(service).toBeTruthy('service has not been created.')
            const serviceAsAny = service as any
            expect(serviceAsAny.activeFeature).toBeUndefined()
            expect(serviceAsAny.featureList).toBeUndefined()
            expect(serviceAsAny.routedComponent).toBeUndefined()
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [DOCK]', () => {
        it('readDockConfiguration: read and return the list of configured Features', fakeAsync(() => {
            const serviceAsAny = service as any
            expect(serviceAsAny.activeFeature).toBeUndefined()
            expect(serviceAsAny.featureList).toBeUndefined()
            spyOn(clientWrapperService, 'wrapHttpRESOURCECall').and
                .callFake(function () {
                    console.log('Code Coverage Phase [DOCK]->spyOn.wrapHttpRESOURCECall')
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(isolationService.directAccessAssetResource('properties/config/DockFeatureMap'))
                        }, 100)
                    })
                })
            service.readDockConfiguration()
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(6)
                })
            tick(1100)
            expect(serviceAsAny.featureList.length).toBe(6)
        }))
        it('activateFeature.null: no feature selected for activation', fakeAsync(() => {
            const serviceAsAny = service as any
            service.readDockConfiguration()
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(6)
                })
            tick(100)
            expect(serviceAsAny.featureList.length).toBe(6)
            serviceAsAny.activeFeature = serviceAsAny.featureList[3]
            expect(serviceAsAny.activeFeature).toBeDefined()
            service.activateFeature(null)
            tick(100)
            expect(serviceAsAny.activeFeature).toBeUndefined()
            expect(serviceAsAny.featureList.length).toBe(6)
            expect(location.path()).toBe('/')
        }))
        it('activateFeature.new feature: activate a new selected feature', fakeAsync(() => {
            // Given
            const serviceAsAny = service as any
            service.readDockConfiguration()
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(6)
                })
            tick(100)
            expect(serviceAsAny.featureList.length).toBe(6)
            expect(serviceAsAny.featureList[3]).toBeDefined()
            expect(serviceAsAny.featureList[3].active).toBeFalse()
            spyOn(serviceAsAny.featureList[3], 'activate').and.callThrough()
            expect(serviceAsAny.activeFeature).toBeUndefined('Initial state is no feature activated.')
            // Test
            service.activateFeature(serviceAsAny.featureList[3])
            tick(100)
            // Expect
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature).toBe(serviceAsAny.featureList[3])
            expect(serviceAsAny.featureList[3].active).toBeTrue()
            expect(serviceAsAny.featureList[3].activate).toHaveBeenCalled()
            expect(location.path()).toBe('/production/pendingjobs')
        }))
        it('activateFeature.replace feature: activate a new selected feature', fakeAsync(() => {
            // Given
            const serviceAsAny = service as any
            service.readDockConfiguration()
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(6)
                })
            tick(100)
            expect(serviceAsAny.featureList.length).toBe(6)
            service.activateFeature(serviceAsAny.featureList[3])
            tick(100)
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature).toBe(serviceAsAny.featureList[3])
            expect(serviceAsAny.featureList[3].active).toBeTrue()
            spyOn(serviceAsAny.featureList[1], 'activate').and.callThrough()
            // Test
            service.activateFeature(serviceAsAny.featureList[1])
            tick(100)
            // Expect
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.featureList[3].active).toBeFalse()
            expect(serviceAsAny.featureList[1].active).toBeTrue()
            expect(serviceAsAny.activeFeature).toBe(serviceAsAny.featureList[1])
            expect(location.path()).toBe('/inventory/partlist')
        }))
        it('clean.success: deactivate any feature', fakeAsync(() => {
            // Given
            const serviceAsAny = service as any
            service.readDockConfiguration()
                .subscribe((response: Feature[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(6)
                })
            tick(100)
            expect(serviceAsAny.featureList.length).toBe(6)
            service.activateFeature(serviceAsAny.featureList[3])
            tick(100)
            expect(serviceAsAny.activeFeature).toBeDefined()
            expect(serviceAsAny.activeFeature).toBe(serviceAsAny.featureList[3])
            expect(serviceAsAny.featureList[3].active).toBeTrue()
            // Test
            service.clean()
            expect(serviceAsAny.activeFeature).toBeUndefined()
        }))
        it('activatePage.success: set the active component to have the refresh target', () => {
            // Given
            const serviceAsAny = service as any
            expect(serviceAsAny.routedComponent).toBeUndefined()
            const pageRef: Refreshable = { clean: () => { }, refresh: () => { } }
            // Test
            service.activatePage(pageRef)
            // Expect
            expect(serviceAsAny.routedComponent).toBeDefined()
        })
        it('refresh.success: refresh the active component', () => {
            // Given
            const serviceAsAny = service as any
            const pageRef: Refreshable = { clean: () => { }, refresh: () => { } }
            spyOn(pageRef, 'refresh')
            service.activatePage(pageRef)
            // Test
            service.refresh()
            // Expect
            expect(pageRef.refresh).toHaveBeenCalled()
        })
    })
})
