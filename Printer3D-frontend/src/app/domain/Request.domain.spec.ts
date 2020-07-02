// - DOMAIN
import { Request } from '@domain/Request.domain';
import { RequestState } from './interfaces/EPack.enumerated';
import { Part } from './Part.domain';
import { RequestItem } from './RequestItem.domain';

xdescribe('CLASS Request [Module: DOMAIN]', () => {
    // let isolation: SupportIsolationService;

    beforeEach(() => {
        // isolation = TestBed.get(SupportIsolationService);
    });

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
            const content: RequestItem = instanceAsAny.partList[0]
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
                "contents": [{
                    "partId": "a047ef17-fa0b-4df8-9f5e-c98de82dc4a2",
                    "quantity": 2
                }, {
                    "partId": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                    "quantity": 4
                }]
            });
            expect(instance).toBeDefined();
            expect(instance.getId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a");
            expect(instance.getLabel()).toBe("Proveedor de boquillas")
            // expect(instance.getPartList()).toBeDefined()
            // expect(instance.getPartList().length).toBe(2)
            // expect(instance.getPartCount()).toBe(6)
            expect(instance.getRequestDate()).toBeDefined()
            const partProvider = {
                findById: () => { return new Part({ price: 2 }) }
            }
            // instance.setPartProvider(partProvider)
            expect(instance.getAmount()).toBe('12 €')
            expect(instance.getState()).toBe(RequestState.OPEN)
            // expect(instance.getParts()).toBeDefined()
            // expect(instance.getParts().length).toBe(2)
        });
    });
});
