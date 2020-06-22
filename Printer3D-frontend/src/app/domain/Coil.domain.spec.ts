// - DOMAIN
import { Coil } from './Coil.domain';

describe('CLASS Coil [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Coil();
            expect(instance).toBeDefined();
            expect(instance.weight).toBeUndefined();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Coil({ id: '-ID-', material: 'PLA', color: 'WHITE', weight: 500 });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            expect(instance.material).toBe('PLA');
            expect(instance.color).toBe('WHITE');
            expect(instance.weight).toBe(500);
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('createNewId: generate a new UUID value', () => {
            const instance = new Coil({ id: '-ID-', material: 'PLA', color: 'WHITE' });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            const obtained = instance.createNewId()
            expect(instance.id).toBe(obtained);
        });
        it('getCoilIdentifier: create a unique human readable identifier', () => {
            const instance :Coil= new Coil({ id: '-ID-', material: 'PLA', color: 'WHITE' });
            expect(instance).toBeDefined();
            const expected : string= 'PLA' + ':' + 'WHITE';
            const obtained = instance.getCoilIdentifier();
            expect(expected).toBe(obtained);
        });
    });
});
