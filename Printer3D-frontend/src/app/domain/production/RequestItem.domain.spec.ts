// - DOMAIN
import { RequestItem } from './RequestItem.domain';
import { RequestContentType } from '../interfaces/EPack.enumerated';
import { Part } from '../inventory/Part.domain';

describe('CLASS RequestItem [Module: DOMAIN]', () => {
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new RequestItem()
            const instanceAsAny = instance as any

            expect(instance).toBeDefined();
            expect(instanceAsAny.itemId).toBeUndefined()
            expect(instanceAsAny.type).toBeUndefined()
            expect(instanceAsAny.quantity).toBe(0)
            expect(instanceAsAny.missing).toBe(0)
            expect(instanceAsAny.required).toBe(0)
            expect(instanceAsAny.content).toBeUndefined()
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters contract', () => {
            const testPart: Part = new Part()
            const instance = new RequestItem({
                "itemId": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "type": RequestContentType.PART,
                "quantity": 5,
                "missing": 2,
                "required": 1,
                "content": testPart
            })
            expect(instance.getQuantity()).toBe(5)
            expect(instance.getMissing()).toBe(2)
            instance.setMissing(0)
            expect(instance.getMissing()).toBe(2)
            instance.setMissing(4)
            expect(instance.getMissing()).toBe(4)
            expect(instance.getContent()).toBe(testPart)
            instance.setContent(new Part({ id: "newpart" }))
            expect(instance.getContent().getId()).toBe("newpart")
            instance.addContent(new Part({ id: "newpartcontent" }))
            expect(instance.getContent().getId()).toBe("newpartcontent")
            expect(instance.getQuantity()).toBe(6)
            expect(instance.getRequired()).toBe(1)
            instance.setRequired(4)
            expect(instance.getRequired()).toBe(4)
            expect(instance.getQuantity()).toBe(6)
            expect(instance.incrementCount()).toBe(7)
            expect(instance.getQuantity()).toBe(7)
            expect(instance.decrementCount()).toBe(6)
            expect(instance.getQuantity()).toBe(6)
        });
        it('IContent contract', () => {
            const testPart: Part = new Part({
                label: "-PART-LABEL-",
                price: 12.34,
                stockAvailable: 321
            })
            const instance = new RequestItem({
                "itemId": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "type": RequestContentType.PART,
                "quantity": 5,
                "missing": 2,
                "required": 1,
                "content": testPart
            })
            expect(instance.getId()).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea")
            expect(instance.getLabel()).toBe("-PART-LABEL-")
            expect(instance.getType()).toBe(RequestContentType.PART)
            expect(instance.getPrice()).toBe(12.34)
            expect(instance.getLabel()).toBe("-PART-LABEL-")
            expect(instance.getStock()).toBe(321)
        });
    });
});
