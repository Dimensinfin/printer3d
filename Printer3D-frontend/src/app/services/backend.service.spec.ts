// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
import { Router } from '@angular/router'
// - TESTING
import { inject } from '@angular/core/testing'
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { BackendService } from './backend.service'
// import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { HttpClientWrapperService } from './httpclientwrapper.service'
import { SupportHttpClientWrapperService } from '../testing/SupportHttpClientWrapperService.service'
// - DOMAIN
import { ResponseTransformer } from './support/ResponseTransformer'
import { Part } from '@domain/inventory/Part.domain'
import { Coil } from '@domain/inventory/Coil.domain'
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto'
import { MachineV2 } from '@domain/production/MachineV2.domain'
import { Job } from '@domain/production/Job.domain'
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { JobRequest } from '@domain/dto/JobRequest.dto'
import { Model } from '@domain/inventory/Model.domain'
import { ModelRequest } from '@domain/dto/ModelRequest.dto'
import { RequestRequest } from '@domain/dto/RequestRequest.dto'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'
import { WeekAmount } from '@domain/dto/WeekAmount.dto'
import { MockResourceAccess } from '@app/testing/MockResourceAccess.service'

export class TestContentProvider implements IContentProvider {
    private parts: Part[] = []

    constructor(private service: BackendService) {
        // service.apiv2_InventoryGetParts()
        //     .subscribe((response: Part[]) => {
        //         this.parts = response
        //         console.log('[TestContentProvider.<constructor>]part count:' + this.parts.length)
        //     })
    }

    public findById(id: string, type: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part
        return undefined
    }
}
describe('SERVICE BackendService [Module: CORE]', () => {
    let service: BackendService
    // let isolationService: IsolationService
    let httpService: MockResourceAccess = new MockResourceAccess()

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
                // { provide: IsolationService, useClass: SupportIsolationService },
                { provide: HttpClientWrapperService, useValue: httpService },
            ]
        }).compileComponents()
        service = TestBed.inject(BackendService)
        // isolationService = TestBed.inject(IsolationService)
    })

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(service).toBeDefined('service has not been created.')
        })
        it('constructor.none: validate initial state', () => {
            expect(service).toBeDefined('service has not been created.')
            const serviceAsAny = service as any
            expect(serviceAsAny.APIV1).toBeDefined('API paths should be defined.')
            expect(serviceAsAny.APIV2).toBeDefined('API paths should be defined.')
            expect(serviceAsAny.APIV3).toBeDefined('API paths should be defined.')
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [ACTUATOR]', () => {
        it('apiActuatorInfo.default: get the backend information record', fakeAsync(() => {
            spyOn(httpService, 'wrapHttpGETCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('actuator.info'))
                        }, 100)
                    })
                })
            service.apiActuatorInfo()
                .subscribe((response: BackendInfoResponse) => {
                    expect(response).toBeDefined()
                    expect(response.getVersion()).toBe('<' + "0.0.0" + ' backend')
                })
            tick(1000)
        }))
    })
    describe('Code Coverage Phase [NEW ENTITIES]', () => {
        it('apiNewPart_v1.default: get the persisted part', fakeAsync(() => {
            const part = new Part()
            spyOn(httpService, 'wrapHttpPOSTCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newpart'))
                        }, 100)
                    })
                })
            service.apiNewPart_v1(part, new ResponseTransformer().setDescription('Transforms data into Part.')
                .setTransformation((entrydata: any): Part => {
                    return new Part(entrydata)
                }))
                .subscribe((response: Part) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Part)
                })
            tick(1000)
        }))
        it('apiNewCoil_v1.default: get the persisted coils', fakeAsync(() => {
            const coil = new Coil()
            spyOn(httpService, 'wrapHttpPOSTCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newcoil'))
                        }, 100)
                    })
                })
            service.apiNewCoil_v1(coil, new ResponseTransformer().setDescription('Transforms data into Coil.')
                .setTransformation((entrydata: any): Coil => {
                    return new Coil(entrydata)
                }))
                .subscribe((response: Coil) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Coil)
                })
            tick(1000)
        }))
        it('apiNewModel_v1.default: get the persisted coils', fakeAsync(() => {
            const model = new ModelRequest()
            spyOn(httpService, 'wrapHttpPOSTCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newmodel'))
                        }, 100)
                    })
                })
            service.apiNewModel_v1(model, new ResponseTransformer().setDescription('Transforms data into Coil.')
                .setTransformation((entrydata: any): Model => {
                    return new Model(entrydata)
                }))
                .subscribe((response: Model) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Model)
                })
            tick(1000)
        }))
    })
    xdescribe('Code Coverage Phase [INVENTORY]', () => {
        it('apiv2_InventoryGetParts.default: download the list of parts', fakeAsync(() => {
            spyOn(httpService, 'wrapHttpGETCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('inventory.parts.v2'))
                        }, 100)
                    })
                })
            service.apiv2_InventoryGetParts()
                .subscribe((response: Part[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(43) // From the total list we should remove the inactive parts
                    expect(response[0]).toBeDefined()
                    expect(response[0] instanceof Part)
                })
            tick(1000)
        }))
        it('apiInventoryUpdatePart_v1.default: update an existing Part', fakeAsync(() => {
            const part: Part = new Part()
            spyOn(httpService, 'wrapHttpPATCHCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newpart'))
                        }, 100)
                    })
                })
            service.apiInventoryUpdatePart_v1(part, new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): Part => {
                    return new Part(entrydata)
                }))
                .subscribe((response: Part) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Part)
                    expect(response.id).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea")
                    expect(response.label).toBe("Covid-19 Key")
                })
            tick(1000)
        }))
        it('apiInventoryGroupUpdatePart_v1.default: update an existing Part', fakeAsync(() => {
            const part: Part = new Part()
            spyOn(httpService, 'wrapHttpPATCHCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newpart'))
                        }, 100)
                    })
                })
            service.apiInventoryGroupUpdatePart_v1(part, new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): Part => {
                    console.log('update part: ' + JSON.stringify(entrydata))
                    return new Part(entrydata)
                }))
                .subscribe((response: Part) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Part)
                    expect(response.id).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea")
                    expect(response.label).toBe("Covid-19 Key")
                })
            tick(1000)
        }))
        it('apiInventoryUpdateModel_v1.default: get the list of Parts', fakeAsync(() => {
            const updatingModel: ModelRequest = new ModelRequest()
            spyOn(httpService, 'wrapHttpPATCHCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('newmodel'))
                        }, 100)
                    })
                })
            service.apiInventoryUpdateModel_v1(updatingModel, new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): any => {
                    return entrydata
                }))
                .subscribe((response: any) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Model)
                })
            tick(1000)
        }))
        it('apiGetFinishings_v1.default: get the list of finishings available', fakeAsync(() => {
            spyOn(httpService, 'wrapHttpGETCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('inventory.finishings'))
                        }, 100)
                    })
                })
            service.apiInventoryGetFinishings_v1(new ResponseTransformer().setDescription('Transforms data into FinishingResponse.')
                .setTransformation((entrydata: any): FinishingResponse => {
                    return new FinishingResponse(entrydata)
                }))
                .subscribe((response: FinishingResponse) => {
                    expect(response).toBeDefined()
                    expect(response instanceof FinishingResponse)
                })
            tick(1000)
        }))
        it('apiInventoryGetMachines_v2.default: get the list of Machines', fakeAsync(() => {
            console.log('apiInventoryMachines_v2.default: get the list of Machines')
            spyOn(httpService, 'wrapHttpGETCall').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(httpService.directAccessMockResource('inventory.machines.v2'))
                        }, 100)
                    })
                })
            service.apiInventoryGetMachines_v2(new ResponseTransformer().setDescription('Transforms Inventory Machine list form backend.')
                .setTransformation((entrydata: any): MachineV2[] => {
                    console.log('apiInventoryMachines_v2.entrydata: ' + JSON.stringify(entrydata))
                    const recordList: MachineV2[] = []
                    for (let entry of entrydata)
                        recordList.push(new MachineV2(entry))
                    return recordList
                }))
                .subscribe((response: MachineV2[]) => {
                    console.log(response)
                    expect(response).toBeDefined()
                    expect(response.length).toBe(8)
                    expect(response[0]).toBeDefined()
                    expect(response[0] instanceof MachineV2)
                    expect(response.length).toBe(8, 'Number of Machines do not match.')
                })
        }))
        it('apiMachinesStartBuild_v2.default: start a build jot on a Machine', async () => {
            const machineId: string = "-MACHINE-ID-"
            const jobRequest: JobRequest = new JobRequest({ part: { id: "-ID-" } })
            service.apiMachinesStartBuild_v2(machineId, jobRequest, new ResponseTransformer().setDescription('Transforms response to a Machine.')
                .setTransformation((entrydata: any): MachineV2 => {
                    return new MachineV2(entrydata)
                }))
                .subscribe((response: MachineV2) => {
                    expect(response).toBeDefined()
                    expect(response instanceof MachineV2)
                })
        })
        it('apiMachinesCancelBuild_v1.default: cancel the build job on a Machine', async () => {
            const machineId: string = "-MACHINE-ID-"
            service.apiMachinesCancelBuild_v1(machineId, new ResponseTransformer().setDescription('Transforms response to a Machine.')
                .setTransformation((entrydata: any): MachineV2 => {
                    return new MachineV2(entrydata)
                }))
                .subscribe((response: MachineV2) => {
                    expect(response).toBeDefined()
                    expect(response instanceof MachineV2)
                })
        })
        it('apiMachinesCompleteBuild_v1.default: complete the build job on a Machine', async () => {
            const machineId: string = "-MACHINE-ID-"
            service.apiMachinesCompleteBuild_v1(machineId, new ResponseTransformer().setDescription('Transforms response to a Machine.')
                .setTransformation((entrydata: any): MachineV2 => {
                    return new MachineV2(entrydata)
                }))
                .subscribe((response: MachineV2) => {
                    expect(response).toBeDefined()
                    expect(response instanceof MachineV2)
                })
        })
        it('apiInventoryGetModels_v1.default: complete the build job on a Machine', async () => {
            service.apiInventoryGetModels_v1(new TestContentProvider(service))
                .subscribe((response: Model[]) => {
                    expect(response).toBeDefined()
                    expect(response instanceof Model)
                })
        })
    })
    xdescribe('Code Coverage Phase [PRODUCTION]', () => {
        it('apiNewRequest_v2.default: get the persisted part', async () => {
            const request = new RequestRequest()
            service.apiNewRequest_v2(request, new ResponseTransformer().setDescription('Transforms data into Part.')
                .setTransformation((entrydata: any): any => {
                    return entrydata
                }))
                .subscribe((response: CustomerRequest) => {
                    expect(response).toBeDefined()
                    expect(response instanceof CustomerRequest)
                })
        })
        it('apiProductionGetJobs_v1.default: get the list jobs required to level the stocks', async () => {
            service.apiProductionGetJobs_v1(new ResponseTransformer().setDescription('Transforms Production Pending Jobs list form backend.')
                .setTransformation((entrydata: any): Job[] => {
                    const jobs: Job[] = []
                    for (let job of entrydata)
                        jobs.push(new Job(job))
                    return jobs
                }))
                .subscribe((response: Job[]) => {
                    expect(response).toBeDefined()
                    expect(response.length).toBe(255)
                    expect(response[0]).toBeDefined()
                    expect(response[0] instanceof Job)
                    expect(response.length).toBe(255, 'Number of Jobs do not match')
                })
        })
        // it('apiProductionGetOpenRequests_v2.default: get the list jobs required to level the stocks', async () => {
        //     service.apiProductionGetOpenRequests_v2(new TestContentProvider(service))
        //         .subscribe((response: any) => {
        //             expect(response).toBeDefined()
        //             expect(response.length).toBe(0)
        //             expect(response[0] instanceof CustomerRequest)
        //         })
        // })
        // it('apiProductionRequestsClose_v2.default: complete a request', async () => {
        //     const requestId: string = "-REQUEST-ID-"
        //     service.apiProductionRequestsClose_v2(requestId)
        //         .subscribe((response: CustomerRequest) => {
        //             expect(response).toBeDefined()
        //             expect(response instanceof CustomerRequest)
        //         })
        // })
        // it('apiProductionDeleteRequest_v1.default: delete a request', async () => {
        //     const requestId: string = "-REQUEST-ID-"
        //     service.apiProductionDeleteRequest_v2(requestId, new ResponseTransformer().setDescription('Transforms Request form backend.')
        //         .setTransformation((entrydata: any): CustomerRequest => {
        //             return new CustomerRequest(entrydata)
        //         }))
        //         .subscribe((response: CustomerRequest) => {
        //             expect(response).toBeDefined()
        //             expect(response instanceof CustomerRequest)
        //         })
        // })
    })
    xdescribe('Code Coverage Phase [ACCOUNTING]', () => {
        it('apiNewRequest_v2.default: get the persisted part', async () => {
            service.apiAccountingRequestAmountsPerWeek_v1(4, new ResponseTransformer().setDescription('Transforms data into WeekAmount.')
                .setTransformation((entrydata: any): any => {
                    return entrydata
                }))
                .subscribe((response: any) => {
                    expect(response).toBeDefined()
                    expect(response[0]).toBeDefined()
                    expect(response[0] instanceof WeekAmount)
                })
        })
    })
})
