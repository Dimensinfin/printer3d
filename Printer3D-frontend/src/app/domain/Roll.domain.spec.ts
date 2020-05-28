// - DOMAIN
import { PartRecord } from './PartRecord.domain';
import { Roll } from './Roll.domain';

describe('CLASS Roll [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Roll();
            expect(instance).toBeDefined();
            expect(instance.weight).toBe(1000);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Roll({ id: '-ID-', label: '-TEST-LABEL-', material: 'PLA', color: 'WHITE', weight: 500 });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            expect(instance.label).toBe('-TEST-LABEL-');
            expect(instance.material).toBe('PLA');
            expect(instance.color).toBe('WHITE');
            expect(instance.weight).toBe(500);
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('createNewId: generate a new UUID value', () => {
            const instance = new Roll({ id: '-ID-', label: '-TEST-LABEL-', material: 'PLA', color: 'WHITE' });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            const obtained = instance.createNewId()
            expect(instance.id).toBe(obtained);
        });
    });
});
