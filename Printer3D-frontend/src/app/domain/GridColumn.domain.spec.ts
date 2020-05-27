// - DOMAIN
import { GridColumn } from './GridColumn.domain';

describe('CLASS GridColumn [Module: DOMAIN]', () => {
    // let isolation: SupportIsolationService;

    beforeEach(() => {
        // isolation = TestBed.get(SupportIsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new GridColumn();
            expect(instance).toBeDefined();
            expect(instance.sortable).toBeTrue();
            expect(instance.filter).toBeFalsy();
            expect(instance.checkboxSelection).toBeFalsy();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new GridColumn({ headerName: '-TEST-HEADER-NAME-', field: '-TEST-FIELD-' });
            expect(instance).toBeDefined();
            expect(instance.headerName).toBe('-TEST-HEADER-NAME-');
             expect(instance.field).toBe('-TEST-FIELD-');
        });
    });
});
