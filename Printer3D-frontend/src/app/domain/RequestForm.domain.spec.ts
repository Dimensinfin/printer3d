// - DOMAIN
import { RequestForm } from './RequestForm.domain';
import { Part } from '@domain/Part.domain';

describe('CLASS RequestForm [Module: DTO]', () => {
    const requestFormData = {
        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
        "label": "Pedido de 100 Covid-19 Key",
        "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
        "partsToServe": []
    }
    let instance: RequestForm;

    beforeEach(() => {
        instance = new RequestForm();
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            expect(instance).toBeDefined();
            expect(instance.id).toBeDefined();
            expect(instance.label).toBeUndefined();
            expect(instance.requestDate).toBeDefined();
            expect(instance.partList).toBeDefined();
            expect(instance.partList.length).toBe(0);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new RequestForm(requestFormData);
            expect(instance).toBeDefined();
            expect(instance.id).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea");
            expect(instance.label).toBe("Pedido de 100 Covid-19 Key");
            expect(instance.requestDate).toBeDefined();
            expect(instance.partList).toBeDefined();
            expect(instance.partList.length).toBe(0);
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('addPart: add a part to the Request', async () => {
            expect(instance.partList).toBeDefined();
            expect(instance.partList.length).toBe(0);
            instance.addPart(new Part());
            expect(instance.partList.length).toBe(1);
        });
        it('removePart: remove a part from the Request', async () => {
            expect(instance.partList).toBeDefined();
            expect(instance.partList.length).toBe(0, 'Number of Parts at creation');
            const part1: Part = new Part({ labe: 'Part1' })
            const part2: Part = new Part({ labe: 'Part2' })
            instance.addPart(part1);
            instance.addPart(part1);
            instance.addPart(part2);
            expect(instance.partList.length).toBe(3, 'Number of parts after all additions.');
            instance.removePart(part1)
            expect(instance.partList.length).toBe(1, 'Number of parts after removal');
        });
    });
});
