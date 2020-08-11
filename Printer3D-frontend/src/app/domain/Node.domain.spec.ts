// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Node } from '@domain/Node.domain';
import { ESeparator } from './interfaces/EPack.enumerated';

xdescribe('CLASS Node Module: DOMAIN]', () => {

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Node();
            const nodeAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(nodeAsAny.jsonClass).toBe('Node')
            expect(nodeAsAny.expanded).toBeFalse()
            expect(nodeAsAny.selected).toBeFalse()
            expect(nodeAsAny.themeColor).toBe(ESeparator.WHITE)
        });
        it('constructor.data: validate initial state with object data', () => {
            const instance = new Node({
                "jsonClass": "Node",
                "expanded": true,
                "selected": true,
                "themeColor": ESeparator.EMPTY
            });
            const nodeAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(nodeAsAny.jsonClass).toBe('Node')
            expect(nodeAsAny.expanded).toBeTrue()
            expect(nodeAsAny.selected).toBeTrue()
            expect(nodeAsAny.themeColor).toBe(ESeparator.EMPTY)
        });
    });

    // - I N T E R F A C E S   P H A S E
    describe('Validate Interface compliance [INode]', () => {
        it('getJsonClass: check the object has a json class name', () => {
            const node = new Node()
            const obtained = node.getJsonClass();
            expect(obtained).toBe('Node');
        });
        it('isActive: nodes are active by default', () => {
            const node = new Node()
            expect(node.isActive()).toBeTrue()
        });
    });
    describe('Validate Interface compliance [ICollaboration]', () => {
        it('collaborate2View: check the collaborartion generated', () => {
            const node = new Node()
            const obtained = node.collaborate2View();
            expect(JSON.stringify(obtained)).toEqual(JSON.stringify([node]));
        });
    });
    describe('Validate Interface compliance [IExpandable]', () => {
        it('isExpandable: check the expandable functionality', () => {
            const node = new Node()
            const obtained = node.isExpandable();
            expect(obtained).toBeFalsy();
        });
        it('isExpanded: check the expandable functionality', () => {
            const node = new Node()
            const obtained = node.isExpanded();
            expect(obtained).toBeFalsy();
        });
        it('collapse: check the expandable functionality', () => {
            const node = new Node()
            node.collapse();
            expect(node.isExpanded()).toBeFalsy();
        });
        it('expand: check the expandable functionality', () => {
            const node = new Node()
            node.expand();
            expect(node.isExpanded()).toBeTrue();
        });
        it('toggleExpanded: check the expandable functionality', () => {
            const node = new Node()
            node.toggleExpanded();
            expect(node.isExpanded()).toBeTrue();
            node.toggleExpanded();
            expect(node.isExpanded()).toBeFalsy();
        });
    });
    describe('Validate Interface compliance [ISelectable]', () => {
        it('toggleSelected: check the selectable functionality', () => {
            const node = new Node()
            expect(node.isSelected()).toBeFalsy();
            node.toggleSelected();
            expect(node.isSelected()).toBeTruthy();
            node.toggleSelected();
            expect(node.isSelected()).toBeFalsy();
        });
        it('isSelected: check the selectable functionality', () => {
            const node = new Node()
            const obtained = node.isSelected();
            expect(obtained).toBeFalsy();
        });
        it('select: check the selectable functionality', () => {
            const node = new Node()
            node.select();
            expect(node.isSelected()).toBeTruthy();
        });
        it('unselect: check the selectable functionality', () => {
            const node = new Node()
            node.unselect();
            expect(node.isSelected()).toBeFalsy();
        });
    });
    // describe('Validate Interface compliance [IMenu]', () => {
    //     it('hasMenu: check the menu avilabillity', () => {
    //         const node = new Node()
    //         expect(node.hasMenu()).toBeFalsy();
    //     });
    // });
    // describe('Validate Interface compliance [IColor]', () => {
    //     it('getThemeColor: check the cplor theming', () => {
    //         const node = new Node()
    //         expect(node.getThemeColor()).toBe(ESeparator.WHITE);
    //     });
    // });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('isEmpty:success check is a null is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty()).toBeTruthy();
        });
        it('isEmpty:success check is a null is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty(null)).toBeTruthy();
        });
        it('isEmpty:success check is a undefined is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty(undefined)).toBeTruthy();
        });
        it('isEmpty:success check is an array is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty([])).toBeTruthy();
        });
        it('isEmpty:success check is a string is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty('')).toBeTruthy();
        });
        it('isEmpty:success check is a object is empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty({})).toBeTruthy();
        });
        it('isEmpty:failure check is an array is not empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty(['not', 'empty'])).toBeFalsy();
        });
        it('isEmpty:failure check is a string is not empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty('not empty')).toBeFalsy();
        });
        it('isEmpty:failure check is a object is not empty', () => {
            const node = new Node()
            let nodeAsAny = node as any;
            expect(nodeAsAny.isEmpty({ not: 'empty' })).toBeFalsy();
        });
    });
});
