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
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';

export class SupportBackendService {
    private httpWrapper: SupportHttpClientWrapperService;

    constructor() {
        this.httpWrapper = new SupportHttpClientWrapperService();
    }

    // - A C T U A T O R - A P I
    // public apiActuatorInfo(transformer: ResponseTransformer): Observable<BackendInfoResponse> {
    // }
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
            const data = this.directAccessMockResource('newrequest')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiNewRequest_v2(newRequest: RequestRequest, transformer: ResponseTransformer): Observable<RequestForm> {
        console.log('[SupportBackendService.apiNewRequest_v2]> Transformation: ' + transformer.description)
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newrequest')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiNewModel_v1(newModel: ModelRequest, transformer: ResponseTransformer): Observable<RequestForm> {
        console.log('[SupportBackendService.apiNewRequest_v1]> Transformation: ' + transformer.description)
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newmodel')
            observer.next(transformer.transform(data));
            observer.complete();
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
            const data = this.directAccessMockResource('newpart')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiInventoryGroupUpdatePart_v1(updatingPart: Part, transformer: ResponseTransformer): Observable<Part> {
        console.log('>[SupportBackendService.apiInventoryGroupUpdatePart_v1]')
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newpart')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiInventoryUpdateModel_v1(updatingModel: ModelRequest, transformer: ResponseTransformer): Observable<Part> {
        console.log('>[SupportBackendService.apiInventoryUpdatePart_v1]')
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newmodel')
            observer.next(transformer.transform(data));
            observer.complete();
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
    public apiMachinesStartBuild_v2(machineId: string, jobRequest: JobRequest, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.machines.cancelbuild')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiMachinesCancelBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.machines.cancelbuild')
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
    public apiInventoryGetModels_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('inventory.models')
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
    public apiProductionGetOpenRequests_v1(transformer: ResponseTransformer): Observable<any> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('production.openrequests')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiProductionGetOpenRequests_v2(transformer: ResponseTransformer): Observable<any> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('production.openrequests.v2')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }
    public apiRequestsClose_v1(requestId: string, transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            const data = this.directAccessMockResource('newrequest')
            observer.next(transformer.transform(data));
            observer.complete();
        });
    }

    private directAccessMockResource(dataIdentifier: string): any {
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        console.log(">[SupportBackendService.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        return rawdata
    }
}
