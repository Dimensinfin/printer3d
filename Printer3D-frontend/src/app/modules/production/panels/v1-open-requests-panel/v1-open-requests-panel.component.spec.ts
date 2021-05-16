// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { EVariant, RequestContentType } from '@domain/interfaces/EPack.enumerated'
import { V1OpenRequestsPanelComponent } from './v1-open-requests-panel.component'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Observable } from 'rxjs'
import { DataToRequestConverter } from '@app/modules/production/domain/DataToRequest.converter'

describe('COMPONENT V1OpenRequestsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1OpenRequestsPanelComponent
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let backendService = {
        apiv2_InventoryGetParts: () => { },
        apiInventoryGetModels_v1: (provider) => { },
        apiProductionGetOpenRequests_v2: (provider) => { }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1OpenRequestsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useValue: backendService }
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1OpenRequestsPanelComponent)
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
            expect(componentAsAny.backendConnections).toBeDefined()
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.page).toBeUndefined()
            expect(componentAsAny.parts).toBeDefined()
            expect(componentAsAny.parts.length).toBe(0)
            expect(componentAsAny.models).toBeDefined()
            expect(componentAsAny.models.length).toBe(0)
        })
    })

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', fakeAsync(() => {
            const componentAsAny = component as any
            console.log('step.01')
            expect(componentAsAny.backendConnections.length).toBe(0)
            spyOn(backendService, 'apiv2_InventoryGetParts').and
                .callFake(function () {
                    const transformer: ResponseTransformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Parts.')
                        .setTransformation((entrydata: any): Part[] => {
                            const recordList: Part[] = []
                            for (let entry of entrydata)
                                recordList.push(new Part(entry));
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
            spyOn(backendService, 'apiProductionGetOpenRequests_v2').and
                .callFake(function (provider) {
                    console.log('step.05.b')
                    const transformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of Requests.')
                        .setTransformation((entrydata: any): CustomerRequest[] => {
                            console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Processing Requests')
                            // Extract requests from the response and convert them to the Request V2 format. Resolve contents id references.
                            const requestList: CustomerRequest[] = []
                            console.log('step.06')
                            const requestConverter: DataToRequestConverter = new DataToRequestConverter(provider)
                            for (let index = 0; index < entrydata.length; index++) {
                                console.log('step.07')
                                requestList.push(requestConverter.convert(entrydata[index]))
                            }
                            return requestList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(isolationService.directAccessTestResource('production.openrequests.v2')))
                        }, 100)
                    })
                })
            console.log('step.02')
            component.ngOnInit()
            tick(1000)
            expect(component.getVariant()).toBe(EVariant.DEFAULT)
            expect(componentAsAny.backendConnections.length).toBe(3) // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(3)
            expect(componentAsAny.renderNodeList.length).toBe(3)
            expect(component.isDownloading()).toBeFalse()
        }))
    })
    describe('Validate Interface compliance [IViewer]', () => {
        it('fireSelectionChanged: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any
            componentAsAny.page = {
                selectRequest: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'selectRequest')
            componentAsAny.target = new CustomerRequest()
            component.fireSelectionChanged()
            expect(component.page.selectRequest).toHaveBeenCalled()
        })
        it('selectRequest: this method is called when the mouse enters a generic node render', () => {
            const componentAsAny = component as any
            componentAsAny.page = {
                selectRequest: (request) => {
                    expect(request).toBeDefined()
                }
            }
            spyOn(component.page, 'selectRequest')
            component.selectRequest(new CustomerRequest())
            expect(component.page.selectRequest).toHaveBeenCalled()
        })
    })
    describe('Validate Interface compliance [IContentProvider]', () => {
        it('findById.found: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'NOTFOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('FOUND', RequestContentType.PART)).toBeDefined()
        })
        it('findById.notfound: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any
            const partList: Part[] = []
            partList.push(new Part({ id: 'FOUND' }))
            partList.push(new Part({ id: 'FOUND' }))
            componentAsAny.parts = partList
            expect(component.findById('NOTFOUND', RequestContentType.PART)).toBeUndefined()
        })
        it('findById.model: as a Part storage export a function to search for Parts', () => {
            const componentAsAny = component as any
            const modelList: Model[] = []
            modelList.push(new Model({ id: 'FOUND' }))
            modelList.push(new Model({ id: 'NOTFOUND' }))
            componentAsAny.models = modelList
            expect(component.findById('FOUND', RequestContentType.MODEL)).toBeDefined()
        })
    })
})
