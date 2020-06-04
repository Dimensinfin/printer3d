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
    public wrapHttpPUTCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpPUTCall]> request: " + _request);
        return Observable.create((observer) => {
            try {
                let data = this.decodeRequestPath(_request);
                if (null == data)
                    observer.next('');
                else
                    observer.next(data);
            } catch (error) {
                console.log("><[HttpClientWrapperService.wrapHttpPUTCall]> error: " + JSON.stringify(error));
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
        if (request.includes('/inventory/machines')) keyword = 'INVENTORY-MACHINES';
        if (request.includes('/production/jobs/pending')) keyword = 'PRODUCTION-JOBS';
        if (request.includes('/inventory/machines/')) {
            if (request.includes('startbuild')) keyword = 'MACHINE-STARTBUILD';
            if (request.includes('cancelbuild')) keyword = 'MACHINE-CANCELBUILD';
        }
        if (request.includes('/production/jobs/pending')) keyword = 'PRODUCTION-JOBS';

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
            case 'INVENTORY-MACHINES':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
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
                            "colorCode": "NARANJA-T",
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
            case 'PRODUCTION-JOBS':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryJobsResponseJson = {
                    "count": 9,
                    "jobs": [{
                        "id": "91175042-3058-4b96-9937-f5549781ccb4",
                        "part": {
                            "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "NARANJA-T",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 3.0,
                            "stockLevel": 5,
                            "stockAvailable": 0,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "24e7290e-e20c-4e0a-8632-485300158d53",
                        "part": {
                            "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "NARANJA-T",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 3.0,
                            "stockLevel": 5,
                            "stockAvailable": 0,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "9be44a8c-9083-4501-a2bb-bf0b3d13d60d",
                        "part": {
                            "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "NARANJA-T",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 3.0,
                            "stockLevel": 5,
                            "stockAvailable": 0,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "b10f701f-ce39-44dd-a1c0-151fc375679b",
                        "part": {
                            "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "NARANJA-T",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 3.0,
                            "stockLevel": 5,
                            "stockAvailable": 0,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "e3ca9515-8389-4bbf-b565-78c72fb5ecfc",
                        "part": {
                            "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "NARANJA-T",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 3.0,
                            "stockLevel": 5,
                            "stockAvailable": 0,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "385e65f0-8d25-4ef5-b07f-21455b0d2552",
                        "part": {
                            "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "BLANCO",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 4.0,
                            "stockLevel": 3,
                            "stockAvailable": 2,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "49e2b71a-6123-4f77-ac25-9029c3a0f336",
                        "part": {
                            "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "BLANCO",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 4.0,
                            "stockLevel": 3,
                            "stockAvailable": 2,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "66be009f-6bc3-469b-80bd-addcc4f66341",
                        "part": {
                            "id": "55236c12-1322-4e32-8285-dac2a45a65fe",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "ROJO",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 4.0,
                            "stockLevel": 3,
                            "stockAvailable": 2,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    },
                    {
                        "id": "c2b3d1f7-f406-42fe-87a4-0463ee31caed",
                        "part": {
                            "id": "55236c12-1322-4e32-8285-dac2a45a65fe",
                            "label": "Covid-19 Key",
                            "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                            "material": "PLA",
                            "colorCode": "ROJO",
                            "buildTime": 30,
                            "cost": 0.85,
                            "price": 4.0,
                            "stockLevel": 3,
                            "stockAvailable": 2,
                            "imagePath": "https://ibb.co/3dGbsRh",
                            "modelPath": "pieza3.sft",
                            "active": true
                        }
                    }
                    ]
                }
                return inventoryJobsResponseJson;
            case 'MACHINE-STARTBUILD':
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
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
                        "colorCode": "NARANJA-T",
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
                console.log("><[HttpClientWrapperService.decodeRequestPath]> match: " + keyword);
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
}
