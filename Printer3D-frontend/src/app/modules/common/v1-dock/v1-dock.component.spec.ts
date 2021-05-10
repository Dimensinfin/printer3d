// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { V1DockComponent } from './v1-dock.component'
import { Feature } from '@bit/innovative.innovative-core.feature-dock/domain/Feature.domain'
import { DockService } from '@app/services/dock.service'
import { Observable } from 'rxjs'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'

describe('COMPONENT V1DockComponent [Module: SHARED]', () => {
    let component: V1DockComponent
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let dockService = {
        readDockConfiguration: () => {
            console.log('V1DockComponent->spyOn.readDockConfiguration')
            const transformer = new ResponseTransformer().setDescription('Do property transformation to "Feature" list.')
                .setTransformation((entrydata: any): Feature[] => {
                    let results: Feature[] = [];
                    if (entrydata instanceof Array) {
                        for (let key in entrydata)
                            results.push(new Feature(entrydata[key]));
                    }
                    return results;
                })
            return new Observable(observer => {
                setTimeout(() => {
                    observer.next(transformer.transform(
                        isolationService.directAccessAssetResource('properties/config/DefaultDockFeatureMap'))
                    )
                }, 100)
            })
        },
        clean: () => { }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1DockComponent
            ],
            providers: [
                { provide: DockService, useValue: dockService },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1DockComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(componentAsAny.configuredFeatures).toBeDefined('field "configuredFeatures" not defined.')
            expect(componentAsAny.configuredFeatures.length).toBe(0)
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.success: validate initialization flow', fakeAsync(() => {
            const componentAsAny = component as any
            component.ngOnInit()
            spyOn(dockService, 'clean')
            tick(1000)
            expect(componentAsAny.configuredFeatures.length).toBe(8)
            expect(dockService.clean).toHaveBeenCalled()
        }))
    })

    // - G E T T E R S   P H A S E
    describe('Getter Phase', () => {
        it('getActiveFeatures.empty: get the list of Features docked', () => {
            expect(component.getActiveFeatures()).toBeDefined()
            expect(component.getActiveFeatures().length).toBe(0)
        })
        it('getActiveFeatures.initilized: get the list of Features docked', fakeAsync(() => {
            const componentAsAny = component as any
            component.ngOnInit()
            spyOn(dockService, 'clean')
            tick(1000)
            expect(component.getActiveFeatures()).toBeDefined()
            expect(component.getActiveFeatures().length).toBe(8)
        }))
    })
})
