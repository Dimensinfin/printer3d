// - DOMAIN
import { FinishingResponse } from './FinishingResponse.dto';

describe('CLASS FinishingResponse [Module: DOMAIN]', () => {
    const finishingsData = {
        "materials": [{
            "material": "PLA",
            "colors": [
                "BLANCO",
                "ROJO"
            ]
        },
        {
            "material": "TPU",
            "colors": [
                "ROJO",
                "GRIS"
            ]
        }
        ]
    }

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new FinishingResponse();
            expect(instance).toBeDefined();
            expect(instance.materials).toBeDefined();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new FinishingResponse(finishingsData);
            expect(instance).toBeDefined();
            expect(instance.materials.length).toBe(2);
        });
    });

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('getMaterials: get the list of material finishings.', () => {
            const instance = new FinishingResponse(finishingsData);
            expect(instance).toBeDefined();
            const obtained = instance.getMaterials();
            expect(obtained.length).toBe(2, 'The count of materials does not match');
        });
    });
});
