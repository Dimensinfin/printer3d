// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
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

@Injectable({
    providedIn: 'root'
})
export class BackendService {
    private APIV1: string;

    constructor(protected httpService: HttpClientWrapperService) {
        this.APIV1 = environment.backendPath + environment.apiVersion1;
    }
    // - B A C K E N D - A P I
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
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryMachines_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as MachineListResponse;
                return response;
            }));
    }
}
