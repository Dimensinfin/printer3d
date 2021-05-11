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
import { includes } from 'cypress/types/lodash';
// - DOMAIN

@Injectable({
    providedIn: 'root'
})
export class SupportHttpClientWrapperService {
    public mockHttpPOSTCall(request: string): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.mockHttpPOSTCall]> request: " + request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('POST:' + request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.mockHttpPOSTCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public mockHttpGETCall(request: string): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> request: " + request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('GET:' + request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }

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
        console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('GET:' + _request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public wrapHttpPUTCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpPUTCall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('PUT:' + _request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpPUTCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public wrapHttpDELETECall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpDELETECall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('DELETE:' + _request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpDELETECall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public wrapHttpPATCHCall(request: string, body: any, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpPATCHCall]> request: " + request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath('PATCH:' + request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[SupportHttpClientWrapperService.wrapHttpPATCHCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    public wrapHttpPOSTCall(_request: string, body: any, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> request: " + _request);
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
                console.log("><[SupportHttpClientWrapperService.wrapHttpGETCall]> error: " + JSON.stringify(error));
                observer.next('');
            }
            observer.complete();
        });
    }
    private decodeRequestPath(request: string): any {
        console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> request: " + request);
        let keyword = '-NOT-FOUND-';
        if (request.includes('/production/requests'))
            return this.directAccessMockResource('production.openrequests');
        if (request.includes('PATCH')) {
            if (request.includes('/inventory/parts'))
                return this.directAccessMockResource('newpart');
        }
        if (request.includes('POST')) {
            if (request.includes('/production/parts'))
                return this.directAccessMockResource('newpart');
            if (request.includes('/production/coils'))
                return this.directAccessMockResource('newcoil');
            if (request.includes('/production/requests'))
                return this.directAccessMockResource('newrequest');
            if (request.includes('/inventory/machines/')) {
                if (request.includes('startbuild'))
                    return this.directAccessMockResource('inventory.machines.startbuild');
            }
        }
        if (request.includes('GET')) {
            if (request.includes('/api/v2/inventory/parts/update'))
                return this.directAccessMockResource('newpart');
            if (request.includes('/api/v2/inventory/part'))
                return this.directAccessMockResource('inventory.parts');
            if (request.includes('/inventory/machines'))
                return this.directAccessMockResource('inventory.machines.v2');
            if (request.includes('/api/v1/accounting/requests/amount/week'))
                return this.directAccessMockResource('accounting.week.amounts');
            if (request.includes('/api/v1/inventory/models'))
                return this.directAccessMockResource('inventory.models');
        }
        if (request.includes('PUT')) {
            if (request.includes('/inventory/machines/')) {
                if (request.includes('cancelbuild'))
                    return this.directAccessMockResource('inventory.machines.cancelbuild');
                if (request.includes('completebuild'))
                    return this.directAccessMockResource('inventory.machines.cancelbuild');
            }
        }
        if (request.includes('/inventory/parts'))
            return this.directAccessMockResource('inventory.parts');
        if (request.includes('/actuator/info'))
            return this.directAccessMockResource('actuator.info');
        if (request.includes('/inventory/coils'))
            return this.directAccessMockResource('inventory.coils');
        if (request.includes('/production/jobs/pending'))
            return this.directAccessMockResource('production.pendingjobs');

        if (request.includes('/inventory/finishings')) keyword = 'INVENTORY-FINISHINGS';

        console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> keyword: " + keyword);
        switch (keyword) {
            case 'INVENTORY-FINISHINGS':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryFinishingsResponseJson = {
                    "materials": [{
                        "material": "PLA",
                        "colors": [
                            "GRIS",
                            "PLATA",
                            "ROJO"
                        ]
                    },
                    {
                        "material": "WER",
                        "colors": [
                            "ROJO"
                        ]
                    },
                    {
                        "material": "TPU",
                        "colors": [
                            "BLANCO"
                        ]
                    }
                    ]
                }
                return inventoryFinishingsResponseJson;
            case 'INVENTORY-COILS':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                return this.directAccessMockResource('inventory.coils');
            default:
                return {};
        }
    }
    public directAccessMockResource(dataIdentifier: string): any {
        console.log(">[SupportHttpClientWrapperService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        console.log(">[SupportHttpClientWrapperService.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        return rawdata
    }
}
