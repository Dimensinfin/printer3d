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
import { Part } from '@domain/inventory/Part.domain';
import { Coil } from '@domain/inventory/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { MachineV2 } from '@domain/production/MachineV2.domain';
import { Job } from '@domain/production/Job.domain';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { ModelForm } from '@domain/inventory/ModelForm.domain';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto';
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto';
import { WeekAmount } from '@domain/dto/WeekAmount.dto';
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform';
import { Model } from '@domain/inventory/Model.domain';
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';
import { DataToRequestConverter } from '@domain/converter/DataToRequest.converter';

@Injectable({
    providedIn: 'root'
})
export class BackendService {
    private APIV1: string;
    private APIV2: string;

    constructor(
        protected httpService: HttpClientWrapperService) {
        this.APIV1 = Printer3DConstants.BACKENDPATH + Printer3DConstants.APIVERSION1;
        this.APIV2 = Printer3DConstants.BACKENDPATH + Printer3DConstants.APIVERSION2;
    }
    // - A C T U A T O R - A P I
    public apiActuatorInfo(transformer: ResponseTransformer): Observable<BackendInfoResponse> {
        const request = Printer3DConstants.BACKENDPATH + '/actuator/info';
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
        console.log(">[BackendService.apiNewPart_v1]> Body: " + JSON.stringify(newPart));
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
    public apiNewModel_v1(newModel: ModelRequest, transformer: ResponseTransformer): Observable<any> {
        const request = this.APIV1 + '/inventory/models';
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
    public apiv2_InventoryGetParts(): Observable<Part[]> {
        const request = this.APIV2 + '/inventory/parts'
        const transformer: ResponseTransformer = new ResponseTransformer()
            .setDescription('Transforms response into a list of Parts.')
            .setTransformation((entrydata: any): Part[] => {
                const recordList: Part[] = []
                for (let entry of entrydata)
                    recordList.push(new Part(entry));
                return recordList
            })
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryParts_v1]> Transformation: " + transformer.description);
                return transformer.transform(data) as Part[]
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
    public apiInventoryGroupUpdatePart_v1(updatingGroupContent: UpdateGroupRequest, transformer: ResponseTransformer): Observable<Part> {
        const request = this.APIV1 + '/inventory/parts/group';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPATCHCall(request, JSON.stringify(updatingGroupContent), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryUpdatePart_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Part;
                return response;
            }));
    }
    public apiInventoryUpdateModel_v1(updatingModel: ModelRequest, transformer: ResponseTransformer): Observable<ModelForm> {
        const request = this.APIV1 + '/inventory/models';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPATCHCall(request, JSON.stringify(updatingModel), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryUpdatePart_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as ModelForm;
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
    public apiInventoryGetMachines_v2(transformer: ResponseTransformer): Observable<MachineV2[]> {
        const request = this.APIV2 + '/inventory/machines';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName)
            .set('x-api-version', 'api v2');
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryMachines_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineV2[];
                return response;
            }));
    }
    public apiMachinesStartBuild_v2(machineId: string, jobRequest: JobRequest, transformer: ResponseTransformer): Observable<MachineV2> {
        const request = this.APIV2 + '/inventory/machines/' + machineId + '/startbuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(jobRequest), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesStartBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineV2;
                return response;
            }));
    }
    public apiMachinesCancelBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<MachineV2> {
        const request = this.APIV1 + '/inventory/machines/' + machineId + '/cancelbuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesCancelBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineV2;
                return response;
            }));
    }
    public apiMachinesCompleteBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<MachineV2> {
        const request = this.APIV1 + '/inventory/machines/' + machineId + '/completebuild';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiMachinesCancelBuild_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineV2;
                return response;
            }));
    }
    public apiInventoryGetModels_v1(itemContainer: IContentProvider): Observable<Model[]> {
        const request = this.APIV1 + '/inventory/models';
        const transformer = new ResponseTransformer()
            .setDescription('Transforms response into a list of Models.')
            .setTransformation((entrydata: any): Model[] => {
                // For each of the Models expand the Parts from the part provider.
                const modelList: Model[] = []
                for (const entry of entrydata) {
                    const model: Model = new Model(entry)
                    for (let index = 0; index < entry.partIdList.length; index++) {
                        const partFound = itemContainer.findById(entry.partIdList[index], 'PART') as Part
                        if (undefined != partFound) model.addPart(partFound)
                    }
                    modelList.push(model)
                }
                return modelList
            })
        let headers = new HttpHeaders()
            .set('x-api-version', 'api v1');
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryGetModels_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as any;
                return response;
            }));
    }

    // - P R O D U C T I O N
    public apiNewRequest_v2(newRequest: RequestRequest, transformer: ResponseTransformer): Observable<CustomerRequest> {
        const request = this.APIV2 + '/production/requests';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newRequest), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewRequest_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as CustomerRequest;
                return response;
            }));
    }
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
    public apiProductionGetOpenRequests_v2(provider: IContentProvider): Observable<CustomerRequest[]> {
        const request = this.APIV2 + '/production/requests'
        const transformer = new ResponseTransformer()
            .setDescription('Transforms response into a list of Requests.')
            .setTransformation((entrydata: any): CustomerRequest[] => {
                console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Processing Requests')
                // Extract requests from the response and convert them to the Request V2 format. Resolve contents id references.
                const requestList: CustomerRequest[] = []
                const requestConverter: DataToRequestConverter = new DataToRequestConverter(provider)
                for (let index = 0; index < entrydata.length; index++) {
                    requestList.push(requestConverter.convert(entrydata[index]))
                }
                return requestList
            })
        let headers = new HttpHeaders()
        headers = headers.set('x-api-version', 'api v2')
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiProductionGetOpenRequests_v2]> Transformation: " + transformer.description);
                return transformer.transform(data) as CustomerRequest[]
            }))
    }
    public apiProductionRequestsClose_v2(requestId: string, transformer: ResponseTransformer): Observable<CustomerRequest> {
        const request = this.APIV2 + '/production/requests/' + requestId + '/close';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpPUTCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiProductionRequestsClose_v2]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as CustomerRequest;
                return response;
            }));
    }
    public apiProductionDeleteRequest_v2(requestId: string, transformer: ResponseTransformer): Observable<any> {
        const request = this.APIV2 + '/production/requests/' + requestId;
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpDELETECall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiProductionDeleteRequest_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data);
                return response;
            }));
    }

    // - A C C O U N T I N G
    public apiAccountingRequestAmountsPerWeek_v1(weeks: number, transformer: ResponseTransformer): Observable<WeekAmount[]> {
        const request = this.APIV1 + '/accounting/requests/amount/week';
        let headers = new HttpHeaders()
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewRequest_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as WeekAmount[];
                return response;
            }));
    }
}
