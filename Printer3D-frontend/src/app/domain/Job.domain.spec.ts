// - DOMAIN
import { Job } from './Job.domain';

describe('CLASS Job [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Job();
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instance.id).toBeUndefined();
            expect(instance.part).toBeUndefined();
            expect(instanceAsAny.priority).toBe(3)
            expect(instanceAsAny.count).toBe(1)
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Job({
                "id": "1682544c-364b-4e30-b097-fd181bcc50a5",
                "part": {
                    "id": "cb3f7075-e364-4c41-8d4b-a31a8f2039fe",
                    "label": "Boquilla Ganesha - Figura",
                    "description": "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables.",
                    "material": "PLA",
                    "color": "ROJO",
                    "buildTime": 90,
                    "cost": 1.0,
                    "price": 6.0,
                    "stockLevel": 5,
                    "stockAvailable": 0,
                    "imagePath": null,
                    "modelPath": null,
                    "active": true
                },
                "priority": 2
            })
            const instanceAsAny = instance as any
            expect(instance).toBeDefined();
            expect(instance.id).toBe("1682544c-364b-4e30-b097-fd181bcc50a5");
            expect(instance.part).toBeDefined();
            expect(instanceAsAny.priority).toBe(2)
            expect(instanceAsAny.count).toBe(1)
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getLabel:success check the "label" field when defined', () => {
            const instance = new Job({
                "id": "1682544c-364b-4e30-b097-fd181bcc50a5",
                "part": {
                    "id": "cb3f7075-e364-4c41-8d4b-a31a8f2039fe",
                    "label": "Boquilla Ganesha - Figura",
                    "description": "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables.",
                    "material": "PLA",
                    "color": "ROJO",
                    "buildTime": 90,
                    "cost": 1.0,
                    "price": 6.0,
                    "stockLevel": 5,
                    "stockAvailable": 0,
                    "imagePath": null,
                    "modelPath": null,
                    "active": true
                },
                "priority": 2
            })
            expect(instance.getId()).toBe("1682544c-364b-4e30-b097-fd181bcc50a5")
            expect(instance.getPart()).toBeDefined()
            expect(instance.getBuildSeconds()).toBe(90 * 60)
            expect(instance.getPriority()).toBe(2)
            expect(instance.getNumber()).toBe(1)
            instance.aggregate()
            expect(instance.getNumber()).toBe(2)
        });
    });
});
