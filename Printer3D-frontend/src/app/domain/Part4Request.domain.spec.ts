// - DOMAIN
import { Part } from './Part.domain';
import { Part4Request } from './Part4Request.domain';

describe('CLASS Part4Request [Module: DOMAIN]', () => {

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Part4Request();
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instanceAsAny.required).toBe(0)
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Part4Request({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true,
                required: 6
            })
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instanceAsAny.required).toBe(6)

            expect(instance.label).toBe("Covid-19 Key")
            expect(instance.description).toBe("This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.")
            expect(instance.material).toBe('PLA')
            expect(instance.color).toBe("WHITE")
            expect(instance.cost).toBe(0.85)
            expect(instance.price).toBe(4)
            expect(instance.buildTime).toBe(15)
            expect(instance.stockLevel).toBe(3);
            expect(instance.stockAvailable).toBe(4);
            expect(instance.imagePath).toBe("https://ibb.co/3dGbsRh")
            expect(instance.modelPath).toBe("pieza3.sft")
            expect(instance.active).toBeTrue();
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [getters]', () => {
        it('getters - contract', () => {
            const instance = new Part4Request({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true,
                required: 6
            })
            expect(instance).toBeDefined();
            expect(instance.getId()).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea");
            expect(instance.getMissed()).toBe(2);
            expect(instance.getRequired()).toBe(6);
            instance.setRequired(3)
            expect(instance.getRequired()).toBe(3);
            expect(instance.getMissed()).toBe(0);
            instance.incrementCount()
            expect(instance.getRequired()).toBe(4);
            expect(instance.getMissed()).toBe(0);
            instance.incrementCount()
            expect(instance.getRequired()).toBe(5);
            expect(instance.getMissed()).toBe(1);
            instance.decrementCount()
            instance.decrementCount()
            expect(instance.getRequired()).toBe(2);
            expect(instance.getMissed()).toBe(0);
        });
    });
});
