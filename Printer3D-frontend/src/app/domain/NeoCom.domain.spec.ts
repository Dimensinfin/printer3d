// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { NeoCom } from './NeoCom.domain';
import { EVariant, ESeparator } from './interfaces/EPack.enumerated';

describe('CLASS NeoCom [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/NeoCom]> should be created');
            expect(new NeoCom()).toBeTruthy();
        });
    });
    // - I N T E R F A C E S   P H A S E
    describe('Validate Interface compliance [INode]', () => {
        it('getJsonClass: check the object has a json class name', () => {
            const neocom = new NeoCom()
            const obtained = neocom.getJsonClass();
            expect(obtained).toBe('NeoCom');
        });
    });
    describe('Validate Interface compliance [ICollaboration]', () => {
        it('collaborate2View: check the collaborartion generated', () => {
            const neocom = new NeoCom()
            const obtained = neocom.collaborate2View();
            expect(JSON.stringify(obtained)).toEqual(JSON.stringify([neocom]));
        });
    });
    describe('Validate Interface compliance [IExpandable]', () => {
        it('isExpandable: check the expandable functionality', () => {
            const neocom = new NeoCom()
            const obtained = neocom.isExpandable();
            expect(obtained).toBeFalsy();
        });
        it('isExpanded: check the expandable functionality', () => {
            const neocom = new NeoCom()
            const obtained = neocom.isExpanded();
            expect(obtained).toBeFalsy();
        });
        it('collapse: check the expandable functionality', () => {
            const neocom = new NeoCom()
            neocom.collapse();
            expect(neocom.isExpanded()).toBeFalsy();
        });
        it('expand: check the expandable functionality', () => {
            const neocom = new NeoCom()
            neocom.expand();
            expect(neocom.isExpanded()).toBeFalsy();
        });
        it('toggleExpanded: check the expandable functionality', () => {
            const neocom = new NeoCom()
            neocom.toggleExpanded();
            expect(neocom.isExpanded()).toBeFalsy();
            neocom.toggleExpanded();
            expect(neocom.isExpanded()).toBeFalsy();
        });
    });
    describe('Validate Interface compliance [ISelectable]', () => {
        it('toggleSelected: check the selectable functionality', () => {
            const neocom = new NeoCom()
            expect(neocom.isSelected()).toBeFalsy();
            neocom.toggleSelected();
            expect(neocom.isSelected()).toBeTruthy();
            neocom.toggleSelected();
            expect(neocom.isSelected()).toBeFalsy();
        });
        it('isSelected: check the selectable functionality', () => {
            const neocom = new NeoCom()
            const obtained = neocom.isSelected();
            expect(obtained).toBeFalsy();
        });
        it('select: check the selectable functionality', () => {
            const neocom = new NeoCom()
            neocom.select();
            expect(neocom.isSelected()).toBeTruthy();
        });
        it('unselect: check the selectable functionality', () => {
            const neocom = new NeoCom()
            neocom.unselect();
            expect(neocom.isSelected()).toBeFalsy();
        });
    });
    describe('Validate Interface compliance [IMenu]', () => {
        it('hasMenu: check the menu avilabillity', () => {
            const neocom = new NeoCom()
            expect(neocom.hasMenu()).toBeFalsy();
        });
    });
    describe('Validate Interface compliance [IColor]', () => {
        it('getThemeColor: check the cplor theming', () => {
            const neocom = new NeoCom()
            expect(neocom.getThemeColor()).toBe(ESeparator.WHITE);
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('isEmpty:success check is a null is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty()).toBeTruthy();
        });
        it('isEmpty:success check is a null is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(null)).toBeTruthy();
        });
        it('isEmpty:success check is a undefined is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(undefined)).toBeTruthy();
        });
        it('isEmpty:success check is an array is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty([])).toBeTruthy();
        });
        it('isEmpty:success check is a string is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty('')).toBeTruthy();
        });
        it('isEmpty:success check is a object is empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty({})).toBeTruthy();
        });
        it('isEmpty:failure check is an array is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty(['not', 'empty'])).toBeFalsy();
        });
        it('isEmpty:failure check is a string is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty('not empty')).toBeFalsy();
        });
        it('isEmpty:failure check is a object is not empty', () => {
            const neocom = new NeoCom()
            let neocomAsAny = neocom as any;
            expect(neocomAsAny.isEmpty({ not: 'empty' })).toBeFalsy();
        });
    });
});
