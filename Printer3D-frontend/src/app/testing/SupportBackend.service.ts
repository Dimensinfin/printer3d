import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Observable } from 'rxjs';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Roll } from '@domain/Roll.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';

export class SupportBackendService {
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        return Observable.create((observer) => {
            observer.next(transformer.transform({
                "partCount": 3,
                "records": [{
                    "label": "Covid - 19 Key - A",
                    "description": "Llavero para evitar tocar manillas y pulsadores durante la campaña de Covi - 19. Modelo A que es el más simple en un solo color y sin refuerzos.",
                    "buildTime": 60,
                    "affinity": "OFF",
                    "stockLevel": 2,
                    "colours": "ALL",
                    "cost": 0.8,
                    "active": true
                },
                {
                    "label": "Covid - 19 Key - A",
                    "description": "Llavero para evitar tocar manillas y pulsadores durante la campaña de Covi - 19. Modelo A que es el más simple en un solo color y sin refuerzos.",
                    "buildTime": 60,
                    "affinity": "OFF",
                    "stockLevel": 2,
                    "colours": "ALL",
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
    public apiNewRoll_v1(newRoll: Roll, transformer: ResponseTransformer): Observable<Roll> {
        const result = Observable.create((observer) => {
            observer.next(transformer.transform(JSON.stringify(newRoll)));
        });
        console.log('[apiNewRoll_v1]' + JSON.stringify(result))
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
}
