// - DOMAIN
import { CoilTransformer } from './CoilTransformer.transformer';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { Coil } from '@domain/Coil.domain';

describe('CLASS CoilTransformer [Module: DOMAIN]', () => {
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
            const instance = new CoilTransformer();
            const instanceAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(instanceAsAny.columnDefinitions).toBeDefined()
            expect(instanceAsAny.columnDefinitions.length).toBe(3);
            expect(instanceAsAny.rowData).toBeDefined()
            expect(instanceAsAny.rowData.length).toBe(0,'Initial data should have 0 records.');
        });
    });

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('getDefinitions: get the list of column definitions.', () => {
            const instance = new CoilTransformer();
            expect(instance).toBeDefined();
            expect(instance.getDefinitions()).toBeDefined()
            expect(instance.getDefinitions().length).toBe(3);
        });
        it('getRecords: get the list of column definitions.', () => {
            const instance = new CoilTransformer();
             expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new CoilRecord())
            expect(instance.getRecords().length).toBe(1);
        });
        it('clear: clear the list of records.', () => {
            const instance = new CoilTransformer();
             expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new CoilRecord())
            expect(instance.getRecords().length).toBe(1);
            instance.clear();
            expect(instance.getRecords().length).toBe(0);
        });
        it('recordCount: clear the list of records.', () => {
            const instance = new CoilTransformer();
             expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.recordCount()).toBe(0);
            instance.addData(new CoilRecord())
            expect(instance.recordCount()).toBe(1);
            instance.clear();
            expect(instance.recordCount()).toBe(0);
        });
        it('addData: clear the list of records.', () => {
            const instance = new CoilTransformer();
             expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new CoilRecord())
            expect(instance.getRecords().length).toBe(1);
            instance.clear();
            expect(instance.getRecords().length).toBe(0);
        });
        it('transform: clear the list of records.', () => {
            const instance = new CoilTransformer();
            expect ( instance.transform(new Coil())).toBeDefined();
        });
    });
});
