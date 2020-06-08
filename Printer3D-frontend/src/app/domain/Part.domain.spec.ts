// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { Part } from './Part.domain';
import { ExpectedConditions } from 'protractor';

describe('CLASS Part [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Part();
            expect(instance).toBeDefined();
            expect(instance.material).toBe('PLA')
            expect(instance.colorCode).toBe('INDEFINIDO')
            expect(instance.stockLevel).toBe(1);
            expect(instance.stockAvailable).toBe(0);
            expect(instance.active).toBeTrue();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            expect(instance.label).toBe('-TEST-LABEL-');
            expect(instance.stockLevel).toBe(8);
            expect(instance.stockAvailable).toBe(0);
            expect(instance.active).toBeFalse();
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('createNewId: generate a new UUID value', () => {
            const instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            const obtained = instance.createNewId()
            expect(instance.id).toBe(obtained);
        });
        it('composePartIdentifier.color: generate the part identifier', () => {
            let instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, colorCode: null });
            expect(instance).toBeDefined();
            instance.colorCode = null;
            expect(instance.composePartIdentifier()).toBe('-TEST-LABEL-' + ':' + 'INDEFINIDO');
            instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', colorCode: 'WHITE' });
            expect(instance.composePartIdentifier()).toBe('-TEST-LABEL-' + ':' + 'WHITE');
        });
        it('isActive: get the part active field', () => {
            let instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, colorCode: null });
            expect(instance.isActive()).toBeFalse();
        });
        it('isExpandable: parts are not expandable', () => {
            let instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, colorCode: null });
            expect(instance.isExpandable()).toBeFalse();
        });
    });
});
