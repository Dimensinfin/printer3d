// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
// - TESTING
import { fakeAsync, tick, waitForAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { BackendService } from '@app/services/backend.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { V1AvailablePartsPanelComponent } from './v1-available-parts-panel.component'
import { Part } from '@domain/inventory/Part.domain'

describe('COMPONENT V1AvailablePartsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1AvailablePartsPanelComponent
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let backendService = {
        apiv2_InventoryGetParts: () => { },
        apiInventoryGetModels_v1: (provider) => { }
    }

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1AvailablePartsPanelComponent
            ],
            providers: [
                { provide: BackendService, useValue: backendService },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1AvailablePartsPanelComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.before: validate initialization flow', () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.isDownloading()).toBeTrue()
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
        })
        it('ngOnInit.after: validate initialization flow', fakeAsync(() => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            spyOn(backendService, 'apiv2_InventoryGetParts').and
                .callFake(function () {
                    console.log('call on init base')
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Parts.')
                        .setTransformation((entrydata: any): Part[] => {
                            const recordList: Part[] = []
                            for (let entry of entrydata) {
                                const part = new Part(entry)
                                part.unavailable = false
                                recordList.push(part)
                            }
                            return recordList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(isolationService.directAccessTestResource('inventory.parts.v2')))
                        }, 100)
                    })
                })
            component.ngOnInit()
            tick(1000)
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(1) // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(42)
            expect(componentAsAny.renderNodeList.length).toBe(42)
            expect(component.isDownloading()).toBeFalse()
        }))
    })
})
