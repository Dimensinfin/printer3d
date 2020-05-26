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
import { PartListResponse } from '@domain/dto/part-list-response.domain';
import { Part } from '@domain/Part.domain';

@Injectable({
    providedIn: 'root'
})
export class BackendService {
    private APIV1: string;

    constructor(protected httpService: HttpClientWrapperService) {
        this.APIV1 = environment.serverName + environment.apiVersion1;

    }
    // - B A C K E N D - A P I
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        // Construct the request to call the backend.
        const request = this.APIV1 + '/inventory/parts';
        let headers = new HttpHeaders() // Additional mockup headers to apisimulation.
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiInventoryParts_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as PartListResponse;
                return response;
            }));
    }
    public apiNewPart_v1(newPart: Part, transformer: ResponseTransformer): Observable<Part> {
        // Construct the request to call the backend.
        const request = this.APIV1 + '/inventory/parts';
        let headers = new HttpHeaders() // Additional mockup headers to apisimulation.
            .set('xapp-name', environment.appName);
        return this.httpService.wrapHttpGETCall(request, JSON.stringify(newPart), headers)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiNewPart_v1]> Transformation: " + transformer.description);
                const response = transformer.transform(data) as Part;
                return response;
            }));
    }
}
