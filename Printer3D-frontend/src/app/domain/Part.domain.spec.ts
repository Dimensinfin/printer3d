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
            expect(instance.id).toBeUndefined()
            expect(instance.label).toBeUndefined()
            expect(instance.description).toBeUndefined()
            expect(instance.material).toBe('PLA')
            expect(instance.color).toBeUndefined()
            expect(instance.cost).toBeUndefined()
            expect(instance.price).toBeUndefined()
            expect(instance.buildTime).toBeUndefined()
            expect(instance.stockLevel).toBe(1);
            expect(instance.stockAvailable).toBe(0);
            expect(instance.imagePath).toBeUndefined()
            expect(instance.modelPath).toBeUndefined()
            expect(instance.active).toBeTrue();
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            })
            expect(instance).toBeDefined();
            expect(instance.id).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea")
            expect(instance.label).toBe("Covid-19 Key")
            expect(instance.description).toBe("This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.")
            expect(instance.material).toBe('PLA')
            expect(instance.color).toBe("WHITE")
            expect(instance.cost).toBe(0.85)
            expect(instance.price).toBe(4)
            expect(instance.buildTime).toBe(15)
            expect(instance.stockLevel).toBe(3);
            expect(instance.stockAvailable).toBe(4);
            expect(instance.imagePath).toBe("https://ibb.co/3dGbsRh")
            expect(instance.modelPath).toBe("pieza3.sft")
            expect(instance.active).toBeTrue();
        });
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [getters]', () => {
        it('createNewId: generate a new UUID value', () => {
            const instance = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            })
            expect(instance).toBeDefined();
            expect(instance.getId()).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea");
            const obtained = instance.createNewId()
            expect(instance.getId()).toBe(obtained);
            expect(instance.getPrice()).toBe(4);
            expect(instance.getAvailable()).toBe(4);
        });
    });
    describe('Coverage Phase [Methods]', () => {
        it('composePartIdentifier.color: generate the part identifier', () => {
            const instance = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            })
            expect(instance).toBeDefined();
            expect(instance.composePartIdentifier()).toBe("Covid-19 Key" + ':' + 'WHITE');
        });
        it('isActive: get the part active field', () => {
            let instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, color: null });
            expect(instance.isActive()).toBeFalse();
        });
        it('isExpandable: parts are not expandable', () => {
            let instance = new Part({ id: '-ID-', label: '-TEST-LABEL-', stockLevel: 8, active: false, color: null });
            expect(instance.isExpandable()).toBeFalse();
        });
    });
});
