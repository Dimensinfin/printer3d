import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Observable } from 'rxjs';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';

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
            }
            ));
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
    public apiGetFinishings_v1(transformer: ResponseTransformer): Observable<FinishingResponse> {
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
}
