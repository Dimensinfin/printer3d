// - DOMAIN
import { Node } from '@domain/Node.domain';

describe('CLASS Node Module: DOMAIN]', () => {
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Node();
            const nodeAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(nodeAsAny.uniqueIdentifier).toBeDefined()
            expect(nodeAsAny.jsonClass).toBe('Node')
            expect(nodeAsAny.expanded).toBeFalse()
            expect(nodeAsAny.selected).toBeFalse()
        });
        it('constructor.data: validate initial state with object data', () => {
            const instance = new Node({
                "expanded": true,
                "selected": true
            });
            const nodeAsAny = instance as any;
            expect(instance).toBeDefined();
            expect(nodeAsAny.uniqueIdentifier).toBeDefined()
            expect(nodeAsAny.jsonClass).toBe('Node')
            expect(nodeAsAny.expanded).toBeTrue()
            expect(nodeAsAny.selected).toBeTrue()
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
        it('equalRef: validate that two nodes are the same or not', () => {
            const node1 = new Node()
            const node2 = new Node()
            expect(node1.equalRef(node2)).toBeFalse();
            expect(node1.equalRef(node1)).toBeTrue();
        });
        it('getUniqueId: get the unique node identifier', () => {
            const node = new Node()
            expect(node.getUniqueId()).toBeDefined()
            const nodeAsAny = node as any
            nodeAsAny.uniqueIdentifier=null
            expect(node.getUniqueId()).toBeDefined()
       });
    });

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
