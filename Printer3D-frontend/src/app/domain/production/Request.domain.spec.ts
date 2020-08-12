// - DOMAIN
import { Request } from '@domain/production/Request.domain';
import { RequestState } from '../interfaces/EPack.enumerated';
import { Part } from '../inventory/Part.domain';
import { RequestItem } from '@domain/production/RequestItem.domain';

describe('CLASS Request [Module: DOMAIN]', () => {
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Request();
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instanceAsAny.id).toBeUndefined();
            expect(instanceAsAny.label).toBeUndefined()
            expect(instanceAsAny.requestdate).toBeUndefined()
            expect(instanceAsAny.state).toBe(RequestState.OPEN);
            expect(instanceAsAny.contents).toBeDefined()
            expect(instanceAsAny.contents.length).toBe(0);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Request({
                "id": "8674832e-8377-4e30-ab01-d6468a312012",
                "label": "Proveedor de llaves",
                "requestDate": "2020-06-18T13:19:28.671Z",
                "state": "COMPLETED",
                "contents": [new RequestItem({
                    "itemId": "a047ef17-fa0b-4df8-9f5e-c98de82dc4a2",
                    "quantity": 2
                })]
            });
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instanceAsAny.id).toBe("8674832e-8377-4e30-ab01-d6468a312012");
            expect(instanceAsAny.label).toBe("Proveedor de llaves")
            expect(instanceAsAny.requestDate).toBe("2020-06-18T13:19:28.671Z", 'Validating the constructor date set')
            expect(instanceAsAny.state).toBe(RequestState.COMPLETED);
            expect(instanceAsAny.contents).toBeDefined()
            expect(instanceAsAny.contents.length).toBe(1);
            const content: RequestItem = instanceAsAny.contents[0]
            expect(content.getId()).toBe("a047ef17-fa0b-4df8-9f5e-c98de82dc4a2");
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters: validate getter contract', () => {
            const instance = new Request({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "label": "Proveedor de boquillas",
                "requestDate": "2020-06-18T13:19:28.671Z",
                "state": "OPEN",
                "contents": [new RequestItem({
                    "itemId": "a047ef17-fa0b-4df8-9f5e-c98de82dc4a2",
                    "type": 'PART',
                    "quantity": 2,
                    "content": {
                        getPrice: () => { return 10 },
                        getQuantity: () => { return 2 }
                    }
                }), new RequestItem({
                    "itemId": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                    "type": 'PART',
                    "quantity": 4,
                    content: {
                        getPrice: () => { return 5 },
                        getQuantity: () => { return 4 }
                    }
                })]
            });
            expect(instance).toBeDefined();
            expect(instance.getId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a");
            expect(instance.getLabel()).toBe("Proveedor de boquillas")
            expect(instance.getRequestDate()).toBeDefined()
            expect(instance.getContents()).toBeDefined()
            expect(instance.getContents().length).toBe(2)
            expect(instance.getContentCount()).toBe(6)
            expect(instance.getAmount()).toBe('40 €')
            expect(instance.getState()).toBe(RequestState.OPEN)
        });
    });
});
