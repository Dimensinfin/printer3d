// - DOMAIN
import { PartListResponse } from './PartListResponse.dto';

describe('CLASS PartListResponse [Module: DOMAIN]', () => {
    const partsData = {
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

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new PartListResponse();
            expect(instance).toBeDefined();
            expect(instance.count).toBe(0);
            expect(instance.parts.length).toBe(0);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new PartListResponse(partsData);
            expect(instance).toBeDefined();
            expect(instance.count).toBe(2);
            expect(instance.parts.length).toBe(2);
        });
    });
});
