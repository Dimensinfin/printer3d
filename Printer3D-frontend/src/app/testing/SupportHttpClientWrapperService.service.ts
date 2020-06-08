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
        if (request.includes('/inventory/parts')) keyword = 'INVENTORY-PARTS';
        if (request.includes('/inventory/coils')) keyword = 'INVENTORY-COILS';
        if (request.includes('/api/v1/inventory/machines')) keyword = 'INVENTORY-MACHINES';
        if (request.includes('/api/v2/inventory/machines')) keyword = 'INVENTORY-MACHINESV2';
        if (request.includes('/production/jobs/pending')) keyword = 'PRODUCTION-JOBS';
        if (request.includes('/inventory/machines/')) {
            if (request.includes('startbuild')) keyword = 'MACHINE-STARTBUILD';
            if (request.includes('cancelbuild')) keyword = 'MACHINE-CANCELBUILD';
        }
        if (request.includes('/production/jobs/pending')) keyword = 'PRODUCTION-JOBS';
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
            case 'INVENTORY-PARTS':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryPartsResponseJson = {
                    "jsonClass": "PartList",
                    "count": 7,
                    "parts": [{
                        "id": "24b470ce-82ba-4a6e-a673-fe0c34be9c5f",
                        "label": "Segunda pieza",
                        "description": "Segunda pieza",
                        "material": "PLA",
                        "colorCode": "ROSA-T",
                        "buildTime": 45,
                        "cost": 1.0,
                        "price": 2.0,
                        "stockLevel": 5,
                        "stockAvailable": 3,
                        "imagePath": null,
                        "modelPath": "Pieza2.STL",
                        "active": true
                    },
                    {
                        "id": "5749bf23-410d-4c94-aa81-ea3f2b4a408b",
                        "label": "Pieza de prueba",
                        "description": "Pieza de prueba",
                        "material": "PLA",
                        "colorCode": "INDEFINIDO",
                        "buildTime": 55,
                        "cost": 1.0,
                        "price": 2.0,
                        "stockLevel": 2,
                        "stockAvailable": 2,
                        "imagePath": null,
                        "modelPath": "Prueba.STL",
                        "active": true
                    },
                    {
                        "id": "a047ef17-fa0b-4df8-9f5e-c98de82dc4a2",
                        "label": "Pieza de Plata",
                        "description": "Pieza de Plata",
                        "material": "PLA",
                        "colorCode": "PLATA",
                        "buildTime": 15,
                        "cost": 0.9,
                        "price": 2.0,
                        "stockLevel": 10,
                        "stockAvailable": 8,
                        "imagePath": null,
                        "modelPath": null,
                        "active": true
                    },
                    {
                        "id": "5caaf805-f3dd-4dfe-9545-eaa3e6300da3",
                        "label": "Ganesha Boquilla - Principal",
                        "description": "Ganesha Boquilla - Principal",
                        "material": "PLA",
                        "colorCode": "GRIS",
                        "buildTime": 300,
                        "cost": 0.45,
                        "price": 3.0,
                        "stockLevel": 15,
                        "stockAvailable": 3,
                        "imagePath": null,
                        "modelPath": "",
                        "active": true
                    },
                    {
                        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "colorCode": "GRIS",
                        "buildTime": 90,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": null,
                        "modelPath": null,
                        "active": true
                    },
                    {
                        "id": "cb3f7075-e364-4c41-8d4b-a31a8f2039fe",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "colorCode": "ROJO",
                        "buildTime": 90,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": null,
                        "modelPath": null,
                        "active": true
                    },
                    {
                        "id": "ad3df8a8-d6a5-41b6-8c49-b0134aae7332",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "colorCode": "PLATA",
                        "buildTime": 90,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": null,
                        "modelPath": null,
                        "active": false
                    }
                    ]
                }
                return inventoryPartsResponseJson;
            case 'INVENTORY-COILS':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
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
            case 'PRODUCTION-JOBS':
                console.log("><[SupportHttpClientWrapperService.decodeRequestPath]> match: " + keyword);
                const inventoryJobsResponseJson = [{
                    "id": "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56",
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
                    "id": "4ff06fbb-8c0e-41fe-970d-48c225546689",
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
                    "id": "94cba495-870a-4adb-8e3b-043e80d3d6a4",
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
                    "id": "1121ca6c-4035-4170-a9ac-3eebd113dcbf",
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
                    "id": "5a1aa8f3-1ea5-4ea8-ba36-5a0bb7085c06",
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
                    "id": "1682544c-364b-4e30-b097-fd181bcc50a5",
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
                return inventoryJobsResponseJson;
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
}
