// - DOMAIN
import { Part } from '@domain/inventory/Part.domain';

xdescribe('CLASS PartTransformer [Module: DOMAIN]', () => {
    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new Part();
            const instanceAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(instanceAsAny.columnDefinitions).toBeDefined()
            expect(instanceAsAny.columnDefinitions.length).toBe(10);
            expect(instanceAsAny.rowData).toBeDefined()
            expect(instanceAsAny.rowData.length).toBe(0, 'Initial data should have 0 records.');
        });
    });
});
