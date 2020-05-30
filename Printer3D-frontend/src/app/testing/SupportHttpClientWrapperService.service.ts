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

const REQUEST_PREFIX = 'http://neocom.infinity.local/api/v1/neocom';

@Injectable({
    providedIn: 'root'
})
export class SupportHttpClientWrapperService {
    public wrapHttpRESOURCECall(request: string): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpRESOURCECall]> request: " + request);
        return Observable.create((observer) => {
            try {
                let data = require('./mock-data' + request.toLowerCase());
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpRESOURCECall]> error: " + JSON.stringify(error));
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
    public wrapHttpPOSTCall(_request: string, body: any, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpGETCall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = {
                    "id": "5696e7b9-cdbe-4996-bb77-0045dd1b732e",
                    "label": "Covid-21 Key",
                    "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                    "cost": 0.85,
                    "price": 4.0,
                    "stockLevel": 1,
                    "active": true
                };
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
        if (request.includes('/inventory/coils')) keyword = 'INVENTORY-COILS';

        console.log("><[HttpClientWrapperService.decodeRequestPath]> keyword: " + keyword);
        switch (keyword) {
            case 'INVENTORY-PARTS':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryPartsResponseJson = {
                    "jsonClass": "PartList",
                    "count": 2,
                    "parts": [{
                        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "colorCode": "WHITE",
                        "buildTime": 15,
                        "cost": 0.85,
                        "price": 4.0,
                        "stockLevel": 3,
                        "stockAvailable": 4,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.sft",
                        "active": true
                    },
                    {
                        "id": "55236c12-1322-4e32-8285-dac2a45a65fe",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "colorCode": "WHITE",
                        "buildTime": 15,
                        "cost": 0.85,
                        "price": 4.0,
                        "stockLevel": 3,
                        "stockAvailable": 4,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.sft",
                        "active": true
                    }
                    ]
                };
                return inventoryPartsResponseJson;
            case 'INVENTORY-COILS':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryCoilsResponseJson = {
                    "count": 3,
                    "coils": [{
                        "id": "5f01a53a-7ede-487d-ac3b-51388a7d8e93",
                        "material": "PLA",
                        "color": "ROJO",
                        "weight": 1000
                    },
                    {
                        "id": "2365aee7-9eba-4ad8-9aa0-b3091147158f",
                        "material": "TPU",
                        "color": "BLANCO",
                        "weight": 250
                    },
                    {
                        "id": "16cd8324-f569-41dd-b66f-0a2e188b7e92",
                        "material": "WER",
                        "color": "ROJO",
                        "weight": 300
                    }
                    ]
                }
                return inventoryCoilsResponseJson;
            default:
                return {};
        }
    }
}
