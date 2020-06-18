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

describe('CLASS Machine [Module: DOMAIN]', () => {

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
                    "color": "NARANJA-T",
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

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('isRunning.idle: check if the machine has a job running', () => {
            let instance = new Machine({
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
            expect(instance.isRunning()).toBeFalse();
        });
        it('isRunning.idle: check if the machine has a job running', () => {
            let instance = new Machine({
                "id": "e18aa442-19cd-4b08-8ed0-9f1917821fac",
                "label": "Ender 3 Pro - A",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                "buildRecord": {
                    "state": "RUNNING",
                    "partCopies": 1,
                    "part": null,
                    "jobInstallmentDate": null,
                    "remainingTime": 0
                }
            });
            expect(instance.isRunning()).toBeTrue();
        });
        it('getRunTime.idle: get the time left to run', () => {
            let instance = new Machine({
                "id": "e18aa442-19cd-4b08-8ed0-9f1917821fac",
                "label": "Ender 3 Pro - A",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                "buildRecord": {
                    "state": "IDLE",
                    "partCopies": 1,
                    "part": null,
                    "jobInstallmentDate": null,
                    "remainingTime": 34
                }
            });
            expect(instance.getRunTime()).toBe(0)
        });
        it('getRunTime.running: get the time left to run', () => {
            let instance = new Machine({
                "id": "e18aa442-19cd-4b08-8ed0-9f1917821fac",
                "label": "Ender 3 Pro - A",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                "buildRecord": {
                    "state": "RUNNING",
                    "partCopies": 1,
                    "part": null,
                    "jobInstallmentDate": null,
                    "remainingTime": 34
                }
            });
            expect(instance.getRunTime()).toBe(34)
        });
    });
});
