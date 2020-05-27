// - DOMAIN
import { PartRecord } from './PartRecord.domain';

describe('CLASS PartRecord [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new PartRecord();
            expect(instance).toBeDefined();
            // expect(instance.colorCode).toBe('INDEFINIDO');
            expect(instance.stockLevel).toBe(1);
            expect(instance.stockAvailable).toBe(0);
            expect(instance.active).toBeTrue();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new PartRecord({ id: '-ID-', label: '-TEST-LABEL-', colorCode: 'WHITE', stockLevel: 8, active: false });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            expect(instance.colorCode).toBe('WHITE');
            expect(instance.label).toBe('-TEST-LABEL-');
            expect(instance.stockLevel).toBe(8);
            expect(instance.stockAvailable).toBe(0);
            expect(instance.active).toBeFalse();
        });
    });
});
