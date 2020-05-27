import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Observable } from 'rxjs';
import { PartListResponse } from '@domain/dto/part-list-response.domain';
import { Part } from '@domain/Part.domain';
import { Roll } from '@domain/Roll.domain';

export class SupportBackendService {
    public apiInventoryParts_v1(transformer: ResponseTransformer): Observable<PartListResponse> {
        return Observable.create((observer) => {
            observer.next(new PartListResponse({
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
            observer.next(new Part({ id: '-ID-' }));
            // observer.complete();
        });
    }
    public apiNewRoll_v1(newRoll: Roll, transformer: ResponseTransformer): Observable<Roll> {
        const result = Observable.create((observer) => {
            observer.next(new Roll({ id: '-ID-' }));
            // observer.complete();
        });
        console.log('[apiNewRoll_v1]' + JSON.stringify(result))
        return result;
    }
}
