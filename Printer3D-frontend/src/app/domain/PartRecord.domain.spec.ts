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
            expect(instance.active).toBe('ACTIVA');
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new PartRecord({ etiqueta: '-ID-',descripcion:'-DESCRIPCION-', color: 'WHITE', stockRequerido: 8, stockDisponible:0 });
            expect(instance).toBeDefined();
            expect(instance.etiqueta).toBe('-ID-');
            expect(instance.descripcion).toBe('-DESCRIPCION-');
            expect(instance.color).toBe('WHITE');
            expect(instance.stockRequerido).toBe(8);
            expect(instance.stockDisponible).toBe(0);
            expect(instance.active).toBe('ACTIVA');
        });
    });
});
