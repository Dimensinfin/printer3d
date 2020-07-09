// - DOMAIN
import { Request } from '../Request.domain';
import { DataToRequestConverter } from './DataToRequest.converter';
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { Model } from '@domain/inventory/Model.domain';
import { Part } from '@domain/Part.domain';

describe('CLASS DataToRequestConverter [Module: CONVERTERS]', () => {
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Construct]', () => {
        it('constructor.empty: create a new Request processing the serialized input', () => {
            const part: Part = new Part({ id: "a219d4b4-ba56-471e-95c2-940cdca192b1" })
            const model: Model = new Model({ id: "f18ec331-f528-487b-8238-b416709121d3" })
            const contentProvider: IContentProvider = {
                findById: (id, type) => {
                    if (type == RequestContentType.PART) return part
                    if (type == RequestContentType.MODEL) return model
                    return null
                }
            } as IContentProvider
            const instance = new DataToRequestConverter(contentProvider);
            const request: Request = instance.convert({
                "id": "bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef",
                "label": "Complete Slot Car Platform P02",
                "requestDate": "2020-06-29T20:00:00.226181Z",
                "state": "OPEN",
                "contents": [{
                    "itemId": "ed36cdfb-e5ae-4275-a163-63b4be4d952c",
                    "type": "PART",
                    "quantity": 2,
                    "missing": 1
                },
                {
                    "itemId": "0f789845-cdc6-48ce-a0ce-cbaf63cffab5",
                    "type": "MODEL",
                    "quantity": 2,
                    "missing": 1
                }
                ]
            })
            expect(instance).toBeDefined();
            expect(request).toBeDefined();
            expect(request.getId()).toBe("bb451b4b-64f3-47aa-8d8c-8fdcdb6108ef");
            expect(request.getLabel()).toBe("Complete Slot Car Platform P02");
            expect(request.getRequestDate()).toBeDefined()
            expect(request.getState()).toBe('OPEN')
            expect(request.getContentCount()).toBe(4)
            expect(request.getContents()[0]).toBeDefined()
            expect(request.getContents().length).toBe(2)
            expect(request.getContents()[0].getType()).toBe(RequestContentType.PART)
            expect(request.getContents()[1].getType()).toBe(RequestContentType.MODEL)
        });
    });
});
