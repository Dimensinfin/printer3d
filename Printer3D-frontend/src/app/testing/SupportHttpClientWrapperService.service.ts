// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { map } from 'rxjs/operators';
// - HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
// - DOMAIN
// import { ValidateAuthorizationTokenResponse } from '@app/domain/dto/ValidateAuthorizationTokenResponse';
// import { environment } from '@env/environment.prod';
// import { NeoComResponse } from '@app/domain/dto/NeoComResponse.dto';
// import { NeoComException } from '@app/platform/NeoComException';
// import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
// import { Corporation } from '@app/domain/Corporation.domain';
// import { SupportAppStoreService } from './SupportAppStore.service';
// import { Pilot } from '@app/domain/Pilot.domain';

const REQUEST_PREFIX = 'http://neocom.infinity.local/api/v1/neocom';

@Injectable({
    providedIn: 'root'
})
export class SupportHttpClientWrapperService {
    public wrapHttpRESOURCECall(request: string): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpRESOURCECall]> request: " + request);
        return Observable.create((observer) => {
            try {
                let data = require('./mock-data' + request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[HttpClientWrapperService.wrapHttpRESOURCECall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public wrapHttpGETCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpGETCall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath(_request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[HttpClientWrapperService.wrapHttpGETCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    private decodeRequestPath(request: string): any {
        console.log("><[HttpClientWrapperService.decodeRequestPath]> request: " + request);
        let keyword = '-NOT-FOUND-';
        if (request.includes('/inventory/parts')) keyword = 'INVENTORY-PARTS';

        console.log("><[HttpClientWrapperService.decodeRequestPath]> keyword: " + keyword);
        switch (keyword) {
            case 'INVENTORY-PARTS':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryPartsResponseJson = {
                    "partCount": 1,
                    "records": [{
                        "label": "Covid - 19 Key - A",
                        "description": "Llavero para evitar tocar manillas y pulsadores durante la campaña de Covi - 19. Modelo A que es el más simple en un solo color y sin refuerzos.",
                        "buildTime": 60,
                        "affinity": "OFF",
                        "stockLevel": 2,
                        "colours": "ALL",
                        "cost": 0.8,
                        "active": true
                    }]

                };
                return inventoryPartsResponseJson;
                break;
            default:
                return {};
        }
    }
}
