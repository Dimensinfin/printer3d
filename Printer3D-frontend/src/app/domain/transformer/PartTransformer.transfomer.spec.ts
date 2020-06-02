// - DOMAIN
import { CoilTransformer } from './CoilTransformer.transformer';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { Coil } from '@domain/Coil.domain';
import { PartTransformer } from './PartTransformer.tranformer';
import { PartRecord } from '@domain/PartRecord.domain';
import { Part } from '@domain/Part.domain';

describe('CLASS PartTransformer [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new PartTransformer();
            const instanceAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(instanceAsAny.columnDefinitions).toBeDefined()
            expect(instanceAsAny.columnDefinitions.length).toBe(9);
            expect(instanceAsAny.rowData).toBeDefined()
            expect(instanceAsAny.rowData.length).toBe(0, 'Initial data should have 0 records.');
        });
    });

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('getDefinitions: get the list of column definitions.', () => {
            const instance = new PartTransformer();
            expect(instance).toBeDefined();
            expect(instance.getDefinitions()).toBeDefined()
            expect(instance.getDefinitions().length).toBe(9);
        });
        it('getRecords: get the list of column definitions.', () => {
            const instance = new PartTransformer();
            expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new PartRecord())
            expect(instance.getRecords().length).toBe(1);
        });
        it('clear: clear the list of records.', () => {
            const instance = new PartTransformer();
            expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new PartRecord())
            expect(instance.getRecords().length).toBe(1);
            instance.clear();
            expect(instance.getRecords().length).toBe(0);
        });
        it('recordCount: clear the list of records.', () => {
            const instance = new PartTransformer();
            expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.recordCount()).toBe(0);
            instance.addData(new PartRecord())
            expect(instance.recordCount()).toBe(1);
            instance.clear();
            expect(instance.recordCount()).toBe(0);
        });
        it('addData: clear the list of records.', () => {
            const instance = new PartTransformer();
            expect(instance).toBeDefined();
            expect(instance.getRecords()).toBeDefined()
            expect(instance.getRecords().length).toBe(0);
            instance.addData(new PartRecord())
            expect(instance.getRecords().length).toBe(1);
            instance.clear();
            expect(instance.getRecords().length).toBe(0);
        });
        it('transform: clear the list of records.', () => {
            const instance = new PartTransformer();
            expect(instance.transform(new Part())).toBeDefined();
        });
    });
});
