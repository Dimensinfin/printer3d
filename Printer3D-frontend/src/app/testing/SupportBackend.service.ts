import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Observable } from 'rxjs';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Machine } from '@domain/Machine.domain';

export class SupportBackendService {
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
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
            }));
            observer.complete();
        });
    }
    public apiNewPart_v1(newPart: Part, transformer: ResponseTransformer): Observable<Part> {
        return Observable.create((observer) => {
            observer.next(transformer.transform(JSON.stringify(newPart)));
        });
    }
    public apiNewCoil_v1(newCoil: Coil, transformer: ResponseTransformer): Observable<Coil> {
        const result = Observable.create((observer) => {
            observer.next(transformer.transform(JSON.stringify(newCoil)));
        });
        console.log('[apiNewCoil_v1]' + JSON.stringify(result))
        return result;
    }
    public apiInventoryGetFinishings_v1(transformer: ResponseTransformer): Observable<FinishingResponse> {
        const result = Observable.create((observer) => {
            observer.next(transformer.transform({
                "materials": [{
                    "material": "PLA",
                    "colors": [
                        "BLANCO",
                        "ROJO"
                    ]
                },
                {
                    "material": "TPU",
                    "colors": [
                        "ROJO",
                        "GRIS"
                    ]
                }
                ]
            }));
        });
        console.log('[apiGetFinishings_v1]' + JSON.stringify(result))
        return result;
    }
    public apiInventoryCoils_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
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
            ));
            observer.complete();
        });
    }
    public apiInventoryGetMachines_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
                "count": 2,
                "machines": [{
                    "id": "d55a5ca6-b1f5-423c-9a47-007439534744",
                    "label": "Ender 3 Pro - B",
                    "model": "Creality 3D Ender 3 Pro",
                    "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                    "currentJobPart": null,
                    "currentPartInstances": 1,
                    "jobInstallmentDate": null
                },
                {
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
                    "currentPartInstances": 2,
                    "jobInstallmentDate": "2020-06-01T22:05:00Z"
                }
                ]
            }));
            observer.complete();
        });
    }
    public apiMachinesStartBuild_v1(transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
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
            }));
            observer.complete();
        });
    }
    public apiMachinesCancelBuild_v1(transformer: ResponseTransformer): Observable<Machine> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
                "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                "label": "Machine B",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.",
                "currentJobPart": null,
                "currentPartInstances": 1,
                "jobInstallmentDate": null
            }));
            observer.complete();
        });
    }
    public apiProductionGetPendingJobs_v1(transformer: ResponseTransformer): Observable<CoilListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
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
            }));
            observer.complete();
        });
    }
}
