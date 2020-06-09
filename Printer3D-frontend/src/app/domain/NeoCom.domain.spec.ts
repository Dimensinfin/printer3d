// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { Part } from './Part.domain';
import { PartContainer } from './PartContainer.domain';
import { Machine } from './Machine.domain';

describe('CLASS NeoCom Module: DOMAIN]', () => {

    beforeEach(() => {
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Machine();
            expect(instance).toBeDefined();
            expect(instance.currentPartInstances).toBe(1)
        });
        it('constructor.objectv1: validate initial state with object data', () => {
            const instance = new Machine({
                "id": "009ab011-03ad-4e84-9a88-25708d1cfd64",
                "label": "Machine B",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.",
                "currentJobPart": {
                    "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                    "label": "Covid-19 Key",
                    "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                    "material": "PLA",
                    "colorCode": "NARANJA-T",
                    "buildTime": 30,
                    "cost": 0.85,
                    "price": 3.0,
                    "stockLevel": 5,
                    "stockAvailable": 0,
                    "imagePath": "https://ibb.co/3dGbsRh",
                    "modelPath": "pieza3.sft",
                    "active": true
                },
                "currentPartInstances": 2,
                "jobInstallmentDate": "2020-06-01T22:05:00Z"
            });
            expect(instance).toBeDefined();
            expect(instance.id).toBe("009ab011-03ad-4e84-9a88-25708d1cfd64");
            expect(instance.label).toBe("Machine B");
            expect(instance.model).toBe("Creality 3D Ender 3 Pro");
            expect(instance.characteristics).toBe("Max size set to 200mm. Has adaptor for flexible plastic filament.");
            expect(instance.currentJobPart).toBeDefined()
            expect(instance.currentPartInstances).toBe(2)
            expect(instance.jobInstallmentDate).toBeDefined()
        });
    });
    it('constructor.objectv2: validate initial state with object data', () => {
        const instance = new Machine({
            "id": "e18aa442-19cd-4b08-8ed0-9f1917821fac",
            "label": "Ender 3 Pro - A",
            "model": "Creality 3D Ender 3 Pro",
            "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
            "buildRecord": {
                "state": "IDLE",
                "partCopies": 1,
                "part": null,
                "jobInstallmentDate": null,
                "remainingTime": 0
            }
        });
        expect(instance).toBeDefined();
        expect(instance.id).toBe("e18aa442-19cd-4b08-8ed0-9f1917821fac");
        expect(instance.label).toBe("Ender 3 Pro - A");
        expect(instance.model).toBe("Creality 3D Ender 3 Pro");
        expect(instance.characteristics).toBe("Max size set to 200mm. Has adaptor for flexible plastic filament.\n");
        expect(instance.buildRecord).toBeDefined()
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
