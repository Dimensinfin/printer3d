// - DOMAIN
import { CoilListResponse } from "./CoilListResponse.dto";

describe('CLASS CoilListResponse [Module: DOMAIN]', () => {
    const coilData = {
        "count": 3,
        "coils": [{
            "id": "5f01a53a-7ede-487d-ac3b-51388a7d8e93",
            "material": "PLA",
            "color": "ROJO",
            "weight": 1000
        },
        {
            "id": "2365aee7-9eba-4ad8-9aa0-b3091147158f",
            "material": "TPU",
            "color": "BLANCO",
            "weight": 250
        },
        {
            "id": "16cd8324-f569-41dd-b66f-0a2e188b7e92",
            "material": "WER",
            "color": "ROJO",
            "weight": 300
        }
        ]
    };

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new CoilListResponse();
            expect(instance).toBeDefined();
            expect(instance.count).toBe(0);
            expect(instance.coils.length).toBe(0);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new CoilListResponse(coilData);
            expect(instance).toBeDefined();
            expect(instance.count).toBe(3, 'The value of the count field does not match');
            expect(instance.coils.length).toBe(3);
            expect(instance.coils[0].id).toBe("5f01a53a-7ede-487d-ac3b-51388a7d8e93");
        });
    });

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('getCoils: get the list of coils.', () => {
            const instance = new CoilListResponse(coilData);
            expect(instance).toBeDefined();
            const obtained = instance.getCoils();
            expect(obtained.length).toBe(3, 'The count of coils does not match');
        });
    });
});
