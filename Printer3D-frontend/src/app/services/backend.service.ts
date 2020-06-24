// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { ResponseTransformer } from './support/ResponseTransformer';
// - ENVIRONMENT
import { environment } from '@env/environment';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { Job } from '@domain/Job.domain';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { Request } from '@domain/Request.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';

@Injectable({
    providedIn: 'root'
})
export class BackendService {
    private APIV1: string;
    private APIV2: string;

    constructor(
        // private isolationService: IsolationService,
        protected httpService: HttpClientWrapperService) {
        this.APIV1 = environment.backendPath + environment.apiVersion1;
        this.APIV2 = environment.backendPath + environment.apiVersion2;
    }
    // - A C T U A T O R - A P I
    public apiActuatorInfo(transformer: ResponseTransformer): Observable<BackendInfoResponse> {
        const request = environment.backendPath + '/actuator/info';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiActuatorInfo]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as BackendInfoResponse;
                return response;
            }));
    }
    // - B A C K E N D - A P I
    // - N E W   E N T I T I E S
    public apiNewPart_v1(newPart: Part, transformer: ResponseTransformer): Observable<Part> {
        const request = this.APIV1 + '/inventory/parts';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newPart), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewPart_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Part;
                return response;
            }));
    }
    public apiNewCoil_v1(newCoil: Coil, transformer: ResponseTransformer): Observable<Coil> {
        const request = this.APIV1 + '/inventory/coils';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newCoil), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewCoil_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Coil;
                return response;
            }));
    }
    public apiNewRequest_v1(newRequest: Request, transformer: ResponseTransformer): Observable<Request> {
        const request = this.APIV1 + '/production/requests';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newRequest), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewRequest_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Request;
                return response;
            }));
    }
    public apiNewModel_v1(newModel: ModelRequest, transformer: ResponseTransformer): Observable<any> {
        const request = this.APIV1 + '/production/requests';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newModel), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewRequest_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as any;
                return response;
            }));
    }
    // - I N V E N T O R Y
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        const request = this.APIV1 + '/inventory/parts';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryParts_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as PartListResponse;
                return response;
            }));
    }
    public apiInventoryUpdatePart_v1(updatingPart: Part, transformer: ResponseTransformer): Observable<Part> {
        const request = this.APIV1 + '/inventory/parts';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPATCHCall(request, JSON.stringify(updatingPart), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryUpdatePart_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Part;
                return response;
            }));
    }
    public apiInventoryGetFinishings_v1(transformer: ResponseTransformer): Observable<FinishingResponse> {
        const request = this.APIV1 + '/inventory/finishings';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiGetFinishings_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as FinishingResponse;
                return response;
            }));
    }
    public apiInventoryCoils_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        const request = this.APIV1 + '/inventory/coils';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryCoils_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as CoilListResponse;
                return response;
            }));
    }
    public apiInventoryGetMachines_v1(transformer: ResponseTransformer): Observable<MachineListResponse> {
        const request = this.APIV1 + '/inventory/machines';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName)
            .set('x-api-version', 'api v1');
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryMachines_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineListResponse;
                return response;
            }));
    }
    public apiInventoryGetMachines_v2(transformer: ResponseTransformer): Observable<MachineListResponse> {
        const request = this.APIV2 + '/inventory/machines';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName)
            .set('x-api-version', 'api v2');
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryMachines_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineListResponse;
                return response;
            }));
    }
    public apiMachinesStartBuild_v1(machineId: string, partId: string, transformer: ResponseTransformer): Observable<Machine> {
        const request = this.APIV2 + '/inventory/machines/' + machineId + '/startbuild/' + partId;
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesStartBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Machine;
                return response;
            }));
    }
    public apiMachinesStartBuild_v2(machineId: string, jobRequest: JobRequest, transformer: ResponseTransformer): Observable<Machine> {
        const request = this.APIV2 + '/inventory/machines/' + machineId + '/startbuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(jobRequest), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesStartBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Machine;
                return response;
            }));
    }
    public apiMachinesCancelBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<Machine> {
        const request = this.APIV1 + '/inventory/machines/' + machineId + '/cancelbuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesCancelBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Machine;
                return response;
            }));
    }
    public apiMachinesCompleteBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<Machine> {
        const request = this.APIV1 + '/inventory/machines/' + machineId + '/completebuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesCancelBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Machine;
                return response;
            }));
    }

    // - P R O D U C T I O N
    public apiProductionGetJobs_v1(transformer: ResponseTransformer): Observable<Job[]> {
        const request = this.APIV1 + '/production/jobs/pending';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiProductionGetJobs_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Job[];
                return response;
            }));
    }
    public apiProductionGetOpenRequests_v1(transformer: ResponseTransformer): Observable<Request[]> {
        const request = this.APIV1 + '/production/requests';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiProductionGetOpenRequests_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Request[];
                return response;
            }));
    }
    public apiRequestsClose_v1(requestId: string, transformer: ResponseTransformer): Observable<Request> {
        const request = this.APIV1 + '/production/requests/' + requestId + '/close';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiRequestsClose_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Request;
                return response;
            }));
    }
}
