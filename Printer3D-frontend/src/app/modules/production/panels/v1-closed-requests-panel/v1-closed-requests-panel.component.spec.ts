// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { fakeAsync, tick, waitForAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { BackendService } from '@app/services/backend.service'
import { ProductionService } from '../../service/production.service'
// - DOMAIN
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Observable } from 'rxjs'
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto'
import { DataToRequestConverter } from '../../converter/DataToRequest.converter'
import { V1ClosedRequestsPanelComponent } from './v1-closed-requests-panel.component'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'

describe('COMPONENT V1ClosedRequestsPanelComponent [Module: PRODUCTION]', () => {
    let component: V1ClosedRequestsPanelComponent
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let backendService = {
        apiv2_InventoryGetParts: () => { },
        apiInventoryGetModels_v1: (provider) => { }
    }
    let productionService = {
        apiv3_ProductionGetClosedRequests: (provider) => { }
    }

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1ClosedRequestsPanelComponent,
            ],
            providers: [
                { provide: BackendService, useValue: backendService },
                { provide: ProductionService, useValue: productionService },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1ClosedRequestsPanelComponent)
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
            spyOn(productionService, 'apiv3_ProductionGetClosedRequests').and
                .callFake(function (provider) {
                    console.log('step.05.b')
                    const transformer = new ResponseTransformer()
                        .setDescription('Transforms response into a list of "CustomerRequests".')
                        .setTransformation((entrydata: any): CustomerRequestResponse[] => {
                            console.log('-[ProductionService.apiv3_ProductionGetClosedRequests]>Processing Requests')
                            // Extract requests from the response and convert them to the CustomerRequest V2 format. Resolve contents id references.
                            const requestList: CustomerRequestResponse[] = []
                            const requestConverter: DataToRequestConverter = new DataToRequestConverter(provider)
                            for (let index = 0; index < entrydata.length; index++) {
                                requestList.push(requestConverter.convert(entrydata[index]))
                            }
                            return requestList
                        })
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(transformer.transform(isolationService.directAccessTestResource('production.closedrequests.v3')))
                        }, 100)
                    })
                })
            console.log('step.02')
            component.ngOnInit()
            tick(1000)
            expect(component.getVariant()).toBe(Printer3DConstants.DEFAULT_VARIANT)
            expect(componentAsAny.backendConnections.length).toBe(3) // This component downloads the Parts and the Requests
            expect(componentAsAny.dataModelRoot.length).toBe(2, 'The expected number of render elements does not match.')
            expect(componentAsAny.renderNodeList.length).toBe(2, 'The expected number of nodes does not match')
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
    })
})
