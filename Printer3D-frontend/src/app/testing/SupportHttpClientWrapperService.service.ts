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
        if (request.includes('validateAuthorizationToken')) keyword = 'VALIDATEAUTHORIZATIONTOKEN';
        if (request.includes('server')) keyword = 'SERVER-INFO';
        if (request.includes('fittings')) keyword = 'FITTING-PILOT';

        console.log("><[HttpClientWrapperService.decodeRequestPath]> keyword: " + keyword);
        switch (keyword) {
            case 'VALIDATEAUTHORIZATIONTOKEN':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const validationResponseJson = {
                    "responseType": "ValidateAuthorizationTokenResponse",
                    "jwtToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiQmV0aCBSaXBsZXkiLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIyMjM2NDciLCJwaWxvdElkIjo5MjIyMzY0N30.Qom8COyZB2sW3bCGm9pnGcIOqw-E2yKDsmGklQW6r9Fhu8jJpkNUv5TUhU2cJjIg5jX3082bZ6eKtRZ3z10vGw",
                    "credential": {
                        "jsonClass": "Credential",
                        "uniqueId": "tranquility/92223647",
                        "accountId": 92223647,
                        "accountName": "Adam Antinoo",
                        "assetsCount": 1476,
                        "walletBalance": 6.309543632E8,
                        "raceName": "Amarr",
                        "dataSource": "tranquility"
                    }
                };
                return validationResponseJson;
                break;
            case 'SERVER-INFO':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const responseJson = {
                    "players": 10528,
                    "server_version": "1585794",
                    "start_time": "2019-10-16T11:06:17Z"
                };
                return responseJson;
                break;
            // case 'FITTING-PILOT':
            //     console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
            //     return require('./mock-data/' + 'pilot.fittings' + '.json');
            //     break;
            default:
                return {};
        }
    }
}
