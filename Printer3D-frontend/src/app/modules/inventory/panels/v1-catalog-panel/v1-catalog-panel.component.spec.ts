// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { fakeAsync, tick, waitForAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { Part } from '@domain/inventory/Part.domain'
import { V1CatalogPanelComponent } from './v1-catalog-panel.component'
import { Model } from '@domain/inventory/Model.domain'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Observable } from 'rxjs'

describe('COMPONENT V1CatalogPanelComponent [Module: PRODUCTION]', () => {
    let component: V1CatalogPanelComponent
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
                V1CatalogPanelComponent,
            ],
            providers: [
                { provide: BackendService, useValue: backendService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1CatalogPanelComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(component).toBeDefined('component has not been created.')
            const componentAsAny = component as any
            expect(component.page).toBeUndefined()
            expect(component.filterInactive).toBeTrue()
            expect(componentAsAny.parts).toBeDefined()
            expect(componentAsAny.parts.length).toBe(0)
            expect(componentAsAny.models).toBeDefined()
            expect(componentAsAny.models.length).toBe(0)
            expect(componentAsAny.projects).toBeDefined()
            expect(componentAsAny.projects.size).toBe(0)
            expect(componentAsAny.partContainers).toBeDefined()
            expect(componentAsAny.partContainers.size).toBe(0)
            expect(componentAsAny.items).toBeDefined()
            expect(componentAsAny.items.length).toBe(0)
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.filter true: validate initialization flow', fakeAsync(() => {
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
                    console.log('step.04.b')
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
            expect(componentAsAny.backendConnections.length).toBe(2) // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(12)
            expect(componentAsAny.renderNodeList.length).toBe(12)
            expect(component.isDownloading()).toBeFalse()
        }))
        it('ngOnInit.filter false: validate initialization flow', fakeAsync(() => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            component.filterInactive = false
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
                    console.log('step.04.b')
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
            expect(componentAsAny.backendConnections.length).toBe(2) // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(12)
            expect(componentAsAny.renderNodeList.length).toBe(12)
            expect(component.isDownloading()).toBeFalse()
        }))
    })
    // - I N T E R A C T I O N S
    describe('Component Interactions', () => {
        it('toggleFilter click', () => {
            spyOn(component, 'refresh')
            component.toggleFilter()
            expect(component.refresh).toHaveBeenCalled()
        })
    })
    // - I N T E R F A C E S
    describe('Validate Interface compliance [IViewer]', () => {
        it('fireSelectionChanged.model: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any
            componentAsAny.page = {
                setSelected: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'setSelected')
            componentAsAny.selection.addSelection(new Model())
            component.fireSelectionChanged()
            expect(component.page.setSelected).toHaveBeenCalled()
        })
        it('fireSelectionChanged.null: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any
            componentAsAny.page = null
            componentAsAny.selection.addSelection(new Model())
        })
        it('fireSelectionChanged.any: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any
            componentAsAny.page = {
                setSelected: (request) => {
                    expect(request).toBeDefined()
                },
                closeEditor: () => { }
            }
            spyOn(component.page, 'setSelected')
            spyOn(component.page, 'closeEditor')
            componentAsAny.selection.addSelection(new CustomerRequest())
            component.fireSelectionChanged()
            expect(component.page.setSelected).not.toHaveBeenCalled()
            expect(component.page.closeEditor).toHaveBeenCalled()
        })
    })
    describe('Validate Interface compliance [IContentProvider]', () => {
        it('findById.found: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'NOTFOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('FOUND', 'PART')).toBeDefined()
        })
        it('findById.notfound: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'FOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('NOTFOUND', 'PART')).toBeUndefined()
        })
    })
})
