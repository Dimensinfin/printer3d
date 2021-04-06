// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { V1RequestableElementsPanelComponent } from './v1-requestable-elements-panel.component'
import { Part } from '@domain/inventory/Part.domain'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { Observable } from 'rxjs'
import { Model } from '@domain/inventory/Model.domain'

describe('COMPONENT V1RequestableElementsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1RequestableElementsPanelComponent
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let backendService = {
        apiv2_InventoryGetParts: () => { },
        apiInventoryGetModels_v1: (provider) => { }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1RequestableElementsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useValue: backendService }
            ]
        }).compileComponents()

        component = TestBed.createComponent(V1RequestableElementsPanelComponent).componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(component).toBeDefined('component has not been created.')
            const componentAsAny = component as any
            expect(componentAsAny.parts).toBeDefined()
            expect(componentAsAny.parts.length).toBe(0)
            expect(componentAsAny.models).toBeDefined()
            expect(componentAsAny.models.length).toBe(0)
            expect(componentAsAny.items).toBeDefined()
            expect(componentAsAny.items.length).toBe(0)
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', fakeAsync(() => {
            component = TestBed.createComponent(V1RequestableElementsPanelComponent).componentInstance
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
            spyOn(backendService, 'apiInventoryGetModels_v1').and
                .callFake(function (provider) {
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Models.')
                        .setTransformation((entrydata: any): Model[] => {
                            // For each of the Models expand the Parts from the part provider.
                            const modelList: Model[] = []
                            for (const entry of entrydata) {
                                const model: Model = new Model(entry)
                                for (let index = 0; index < entry.partIdList.length; index++) {
                                    const partFound = provider.findById(entry.partIdList[index], 'PART')
                                    if (undefined != partFound) model.addPart(partFound)
                                }
                                modelList.push(model)
                            }
                            return modelList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(isolationService.directAccessTestResource('inventory.models')))
                        }, 100)
                    })
                })
            component.ngOnInit()
            tick(1000)
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(2)
            expect(componentAsAny.parts.length).toBe(13 + 3)
            expect(componentAsAny.models.length).toBe(1 + 1)
            expect(componentAsAny.items.length).toBe(13 + 3 + 2 - 3 - 1)
        }))
    })

    // - C O V E R A G E   P H A S E
    describe('Validate Interface compliance [IContentProvider]', () => {
        it('findById: serach element', () => {
            const componentAsAny = component as any
            componentAsAny.parts = [new Part({ id: "6801b340-a572-4043-85d4-a9e10634e916" })]
            expect(component.findById("-not-found-", 'PART')).toBeUndefined()
            expect(component.findById("6801b340-a572-4043-85d4-a9e10634e916", 'PART')).toBeDefined()
        })
    })

    // - F U N C T I O N A L I T Y   P H A S E
    describe('Validate Functionality for filter [Part Filter]', () => {
        it('filterOutUnavailable: verify that unavailable parts are not returned.', fakeAsync(() => {
            component = TestBed.createComponent(V1RequestableElementsPanelComponent).componentInstance
            component.clean()
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            spyOn(backendService, 'apiv2_InventoryGetParts').and
                .callFake(function () {
                    console.log('call on init filter')
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Parts.')
                        .setTransformation((entrydata: any): Part[] => {
                            const recordList: Part[] = []
                            for (let entry of entrydata)
                                recordList.push(new Part(entry))
                            return recordList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            const data = transformer.transform(isolationService.directAccessTestResource('inventory.parts.v2'))
                            console.log('apiv2_InventoryGetParts.part count: ' + data.length)
                            observer.next(data)
                        }, 100)
                    })
                })
            spyOn(backendService, 'apiInventoryGetModels_v1').and
                .callFake(function (provider) {
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Models.')
                        .setTransformation((entrydata: any): Model[] => {
                            // For each of the Models expand the Parts from the part provider.
                            const modelList: Model[] = []
                            for (const entry of entrydata) {
                                const model: Model = new Model(entry)
                                for (let index = 0; index < entry.partIdList.length; index++) {
                                    const partFound = provider.findById(entry.partIdList[index], 'PART')
                                    if (undefined != partFound) model.addPart(partFound)
                                }
                                modelList.push(model)
                            }
                            return modelList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(isolationService.directAccessTestResource('inventory.models')))
                        }, 100)
                    })
                })
            component.ngOnInit()
            tick(1000)
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(2)
            expect(componentAsAny.parts.length).toBe(13 + 3 - 2)
            expect(componentAsAny.models.length).toBe(1 + 1)
            expect(componentAsAny.items.length).toBe(13 + 3 + 2 - 3 - 1 - 1)
        }))
    })
})
