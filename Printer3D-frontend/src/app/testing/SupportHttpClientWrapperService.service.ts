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

const REQUEST_PREFIX = 'http://neocom.infinity.local/api/v1/neocom';

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
                let data = this.decodeRequestPath(_request);
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
                let data = this.decodeRequestPath(_request);
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
        }
        if (request.includes('GET')) {
            if (request.includes('/api/v2/inventory/parts/update'))
                return this.directAccessMockResource('newpart');
            if (request.includes('/api/v2/inventory/machines/cancelbuild'))
                return this.directAccessMockResource('inventory.machines.cancelbuild');
        }
        if (request.includes('/inventory/parts'))
            return this.directAccessMockResource('inventory.parts');
        if (request.includes('/actuator/info'))
            return this.directAccessMockResource('actuator.info');
        if (request.includes('/inventory/coils'))
            return this.directAccessMockResource('inventory.coils');
        if (request.includes('/production/jobs/pending'))
            return this.directAccessMockResource('production.pendingjobs');

        if (request.includes('/api/v1/inventory/machines')) keyword = 'INVENTORY-MACHINES';
        if (request.includes('/api/v2/inventory/machines')) keyword = 'INVENTORY-MACHINESV2';
        if (request.includes('/inventory/machines/')) {
            if (request.includes('startbuild')) keyword = 'MACHINE-STARTBUILD';
            if (request.includes('cancelbuild')) keyword = 'MACHINE-CANCELBUILD';
        }
        // if (request.includes('/production/jobs/pending')) keyword = 'PRODUCTION-JOBS';
        if (request.includes('/inventory/finishings')) keyword = 'INVENTORY-FINISHINGS';
        // if (request.includes('/api/v2/inventory/parts/update')) keyword = 'INVENTORY-UPDATEPART';

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
            case 'INVENTORY-MACHINES':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryMachinesResponseJson = {
                    "count": 2,
                    "machines": [{
                        "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                        "label": "Machine A",
                        "model": "Creality 3D Ender 3 Pro",
                        "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament."
                    },
                    {
                        "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                        "label": "Machine B",
                        "model": "Creality 3D Ender 3 Pro",
                        "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.",
                        "currentPart": {
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
                        "jobInstallmentDate": "2020-06-01T22:05:00Z"
                    }
                    ]
                }
                return inventoryMachinesResponseJson;
            case 'INVENTORY-MACHINESV2':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryMachinesv2ResponseJson = {
                    "machines": [{
                        "id": "d55a5ca6-b1f5-423c-9a47-007439534744",
                        "label": "Ender 3 Pro - B",
                        "model": "Creality 3D Ender 3 Pro",
                        "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                        "buildRecord": {
                            "state": "IDLE",
                            "part": null,
                            "partCopies": 1,
                            "jobInstallmentDate": null,
                            "remainingTime": 0
                        },
                        "remainingTime": 0,
                        "running": false
                    },
                    {
                        "id": "e18aa442-19cd-4b08-8ed0-9f1917821fac",
                        "label": "Ender 3 Pro - A",
                        "model": "Creality 3D Ender 3 Pro",
                        "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                        "buildRecord": {
                            "state": "IDLE",
                            "part": null,
                            "partCopies": 1,
                            "jobInstallmentDate": null,
                            "remainingTime": 0
                        },
                        "remainingTime": 0,
                        "running": false
                    }
                    ],
                    "count": 2
                }
                return inventoryMachinesv2ResponseJson;
            // case 'PRODUCTION-JOBS':
            //     console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
            //     return this.directAccessMockResource('production.pendingjobs');
            case 'MACHINE-STARTBUILD':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const machineStartBuildResponseJson = {
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
                    "currentPartInstances": 1,
                    "jobInstallmentDate": "2020-06-01T22:05:00Z"
                };
                return machineStartBuildResponseJson;
            case 'MACHINE-CANCELBUILD':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const machineCancelBuildResponseJson = {
                    "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                    "label": "Machine B",
                    "model": "Creality 3D Ender 3 Pro",
                    "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.",
                    "currentJobPart": null,
                    "currentPartInstances": 1,
                    "jobInstallmentDate": null
                };
                return machineCancelBuildResponseJson;
            default:
                return {};
        }
    }
    private directAccessMockResource(dataIdentifier: string): any {
        console.log(">[SupportHttpClientWrapperService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        console.log(">[SupportHttpClientWrapperService.directAccessMockResource]> path: " +
            '../../../support/printer3d-backend-simulation/responses/' + dataIdentifier + '.json');
        return rawdata
    }
}
