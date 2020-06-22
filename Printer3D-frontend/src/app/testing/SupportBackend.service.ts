import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Observable } from 'rxjs';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { SupportHttpClientWrapperService } from './SupportHttpClientWrapperService.service';
import { RequestForm } from '@domain/RequestForm.domain';

export class SupportBackendService {
    private httpWrapper: SupportHttpClientWrapperService;

    constructor() {
        this.httpWrapper = new SupportHttpClientWrapperService();
    }

    // - N E W   E N T I T I E S
    public apiNewPart_v1(newPart: Part, transformer: ResponseTransformer): Observable<Part> {
        console.log('[SupportBackendService.apiNewPart_v1]> Transformation: ' + transformer.description)
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newpart')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiNewCoil_v1(newCoil: Coil, transformer: ResponseTransformer): Observable<Coil> {
        console.log('[SupportBackendService.apiNewCoil_v1]> Transformation: ' + transformer.description)
        return Observable.create((observer) => {
            this.httpWrapper.mockHttpPOSTCall('/production/coils')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    public apiNewRequest_v1(newRequest: RequestForm, transformer: ResponseTransformer): Observable<RequestForm> {
        console.log('[SupportBackendService.apiNewRequest_v1]> Transformation: ' + transformer.description)
        return Observable.create((observer) => {
            this.httpWrapper.mockHttpPOSTCall('/production/requests')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    // - I N V E N T O R Y
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        console.log('>[SupportBackendService.apiInventoryParts_v1]')
        return Observable.create((observer) => {
            this.httpWrapper.wrapHttpGETCall('/api/v2/inventory/parts')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    public apiInventoryUpdatePart_v1(updatingPart: Part, transformer: ResponseTransformer): Observable<Part> {
        console.log('>[SupportBackendService.apiInventoryUpdatePart_v1]')
        return Observable.create((observer) => {
            this.httpWrapper.mockHttpGETCall('/api/v2/inventory/parts/update')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    public apiInventoryGetFinishings_v1(transformer: ResponseTransformer): Observable<FinishingResponse> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.finishings')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiInventoryCoils_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            this.httpWrapper.wrapHttpGETCall('/api/v2/inventory/coils')
                .subscribe(data => {
                    // console.log('-[apiInventoryCoils_v1]> Data: '+JSON.stringify(data))
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    public apiInventoryGetMachines_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
                "count": 2,
                "machines": [{
                    "id": "d55a5ca6-b1f5-423c-9a47-007439534744",
                    "label": "Ender 3 Pro - B",
                    "model": "Creality 3D Ender 3 Pro",
                    "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                    "currentJobPart": null,
                    "currentPartInstances": 1,
                    "jobInstallmentDate": null
                },
                {
                    "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                    "label": "Machine B",
                    "model": "Creality 3D Ender 3 Pro",
                    "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.",
                    "currentJobPart": {
                        "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "color": "NARANJA-T",
                        "buildTime": 30,
                        "cost": 0.85,
                        "price": 3.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.sft",
                        "active": true
                    },
                    "currentPartInstances": 2,
                    "jobInstallmentDate": "2020-06-01T22:05:00Z"
                }
                ]
            }));
            observer.complete();
        });
    }
    public apiInventoryGetMachines_v2(transformer: ResponseTransformer): Observable<CoilListResponse> {
        console.log('>[SupportBackendService.apiInventoryGetMachines_v2]')
        return Observable.create((observer) => {
            this.httpWrapper.wrapHttpGETCall('/api/v2/inventory/machines')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }

    public apiMachinesStartBuild_v1(machineId: string, partId: string, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            this.httpWrapper.wrapHttpGETCall('/api/v2/inventory/machines/startbuild')
                .subscribe(data => {
                    observer.next(transformer.transform(data));
                    observer.complete();
                })
        });
    }
    public apiMachinesCancelBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.machines.cancelbuild')
            // console.log('>[SupportBackendService.apiMachinesCancelBuild_v1]> Data: ' + data)
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiMachinesCompleteBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.machines.cancelbuild')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }

    // - P R O D U C T I O N
    public apiProductionGetJobs_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('production.pendingjobs')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiProductionGetOpenRequests_v1( transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('production.openrequests')
            // console.log('>[SupportBackendService.apiProductionGetOpenRequests_v1]> Data: ' + data)
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    private directAccessMockResource(dataIdentifier: string): any {
        // console.log(">[SupportBackendService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        console.log(">[SupportBackendService.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        return rawdata
    }
}
