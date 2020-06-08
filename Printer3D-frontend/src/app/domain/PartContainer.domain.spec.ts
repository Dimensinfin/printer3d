// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { Part } from './Part.domain';
import { PartContainer } from './PartContainer.domain';

describe('CLASS PartContainer [Module: DOMAIN]', () => {

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new PartContainer();
            expect(instance).toBeDefined();
            expect(instance.contents).toBeDefined()
            expect(instance.contents.length).toBe(0)
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new PartContainer({
                id: '-ID-',
                label: '-TEST-LABEL-',
                description: "-TEST-DESCRIPTION-",
                buildTime: 30,
                imagePath: "-IMAGE-PATH-",
                modelPath: "-MODEL-PATH-",
                contents: ["ONE", "TWO"]
            });
            expect(instance).toBeDefined();
            expect(instance.id).toBe('-ID-');
            expect(instance.label).toBe('-TEST-LABEL-');
            expect(instance.description).toBe("-TEST-DESCRIPTION-");
            expect(instance.buildTime).toBe(30);
            expect(instance.imagePath).toBe("-IMAGE-PATH-")
            expect(instance.modelPath).toBe("-MODEL-PATH-")
            expect(instance.contents).toBeDefined()
            expect(instance.contents.length).toBe(2)
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('isActive: part containers are always active', () => {
            let instance = new PartContainer({ active: false, expandable: false });
            expect(instance.isActive()).toBeTrue();
        });
        it('isExpandable: is ever expnadable', () => {
            let instance = new PartContainer({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, colorCode: null });
            expect(instance.isExpandable()).toBeTrue();
        });
        it('addPart: check the code to add a new part', () => {
            let instance = new PartContainer();
            expect(instance.contents).toBeDefined()
            expect(instance.contents.length).toBe(0)
            instance.addPart(new Part())
            expect(instance.contents.length).toBe(1)
        });
    });
    describe('Validating interfaces [ICollaboration]', () => {
        it('collaborate2View.compressed: generate the nodes that this instance collaborates', () => {
            let instance = new PartContainer();
            expect(instance.contents).toBeDefined()
            expect(instance.contents.length).toBe(0)
            expect(instance.collaborate2View().length).toBe(1)
            instance.addPart(new Part())
            expect(instance.collaborate2View().length).toBe(1)
        });
        it('collaborate2View.expanded: generate the nodes that this instance collaborates', () => {
            let instance = new PartContainer();
            expect(instance.contents).toBeDefined()
            expect(instance.contents.length).toBe(0)
            expect(instance.collaborate2View().length).toBe(1)
            instance.addPart(new Part())
            instance.addPart(new Part())
            instance.expand()
            expect(instance.collaborate2View().length).toBe(3)
        });
    });
});
