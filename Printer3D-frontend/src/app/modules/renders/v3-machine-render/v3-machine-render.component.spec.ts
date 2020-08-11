// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
// - DOMAIN
import { Machine } from '@domain/production/Machine.domain';
import { Part } from '@domain/inventory/Part.domain';
import { V3MachineRenderComponent } from './v3-machine-render.component';
import { Job } from '@domain/production/Job.domain';
import { V1BuildCountdownTimerPanelComponent } from '../v1-build-countdown-timer-panel/v1-build-countdown-timer-panel.component';

const TEST_TIME: number = 12 * 60;

xdescribe('COMPONENT V3MachineRenderComponent [Module: SHARED]', () => {
    let component: V3MachineRenderComponent;
    let fixture: ComponentFixture<V3MachineRenderComponent>;
    let isolationService: SupportIsolationService;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V3MachineRenderComponent,
                V1BuildCountdownTimerPanelComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(V3MachineRenderComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(SupportIsolationService)
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.buildTimeTimer).toBeUndefined();
            expect(component.self).toBeDefined()
            expect(componentAsAny.target).toBeUndefined();
            expect(componentAsAny.state).toBe('IDLE');
            expect(componentAsAny.remainingTime).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngAfterViewInit.idle: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            componentAsAny.node = new Machine()
            await component.ngAfterViewInit();
            jasmine.clock().tick(500);
            expect(component.self).toBeDefined();
            jasmine.clock().uninstall()
        });
        it('ngAfterViewInit.running: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            componentAsAny.node = new Machine({
                "id": "d55a5ca6-b1f5-423c-9a47-007439534744",
                "label": "Ender 3 Pro - B",
                "model": "Creality 3D Ender 3 Pro",
                "characteristics": "Max size set to 200mm. Has adaptor for flexible plastic filament.\n",
                "buildRecord": {
                    "state": "RUNNING",
                    "partCopies": 1,
                    "part": {
                        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "color": "BLANCO",
                        "weight": 5,
                        "buildTime": 60,
                        "cost": 0.65,
                        "price": 2.0,
                        "stockLevel": 3,
                        "stockAvailable": 0,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.STL",
                        "active": true
                    },
                    "jobInstallmentDate": "2020-07-24T10:48:52.391425Z",
                    "remainingTime": 2800
                }
            })
            await component.ngAfterViewInit();
            jasmine.clock().tick(500);
            expect(component.self).toBeDefined();
            expect(componentAsAny.node).toBeDefined();
            expect(component.target).toBeDefined();
            expect(component.isRunning()).toBeTrue();
            expect(componentAsAny.remainingTime).toBe(2800);
            jasmine.clock().uninstall()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getters contract: check all getters', () => {
            component.node = new Machine({
                id: "0913f2bc-6feb-4c80-9093-4372ec630044",
                label: '-MACHINE-LABEL-',
                model: '-MACHINE-MODEL-',
                characteristics: '-MACHINE-CHARACTERISTICS-',
                buildRecord: {
                    "state": "IDLE",
                    "partCopies": 1,
                    "part": null,
                    "jobInstallmentDate": null,
                    "remainingTime": 0
                }
            })
            component.target = new Job({
                id: "0913f2bc-6feb-4c80-9093-4372ec630044",
                part: new Part({
                    id: "0913f2bc-6feb-4c80-9093-4372ec630044",
                    label: '-PART-LABEL-'
                })
            })
            expect(component.getUniqueId()).toBe("0913f2bc-6feb-4c80-9093-4372ec630044")
            expect(component.getLabel()).toBe('-MACHINE-LABEL-')
            expect(component.getModel()).toBe('-MACHINE-MODEL-')
            expect(component.getCharacteristics()).toBe('-MACHINE-CHARACTERISTICS-')
            expect(component.getPartLabel()).toBe('-PART-LABEL-')
            expect(component.isRunning()).toBeFalse()
            component.node = new Machine({
                id: "0913f2bc-6feb-4c80-9093-4372ec630044",
                label: '-MACHINE-LABEL-',
                model: '-MACHINE-MODEL-',
                characteristics: '-MACHINE-CHARACTERISTICS-',
                buildRecord: {
                    "state": "RUNNING",
                    "partCopies": 1,
                    "part": null,
                    "jobInstallmentDate": null,
                    "remainingTime": 0
                }
            })
            expect(component.isRunning()).toBeTrue()
        });
    });
    describe('Code Coverage Phase [Methods]', () => {
        it('completeTime: receive the signal to complete the job', () => {
            const componentAsAny = component as any;
            component.completeTime();
            expect(componentAsAny.state).toBe('COMPLETED');
        });
        it('onDrop.empty: drop an empty element', () => {
            component.onDrop(null);
            expect(component.target).toBeUndefined();
        });
        it('onDrop.job: drop an element', () => {
            const componentAsAny = component as any;
            expect(component.target).toBeUndefined()
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            component.onDrop({
                dragData: {
                    "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                    part: new Part({
                        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "color": "BLANCO",
                        "weight": 5,
                        "buildTime": 60,
                        "cost": 0.65,
                        "price": 2.0,
                        "stockLevel": 3,
                        "stockAvailable": 0,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.STL",
                        "active": true
                    })
                }
            });
            expect(component.target).toBeDefined()
            expect(component.isRunning()).toBeFalse()
            expect(componentAsAny.remainingTime).toBe(3600)
        });
        it('changePartCount: change the number of copies', () => {
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            component.onDrop({
                dragData: {
                    "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                    part: new Part({
                        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "color": "BLANCO",
                        "weight": 5,
                        "buildTime": 60,
                        "cost": 0.65,
                        "price": 2.0,
                        "stockLevel": 3,
                        "stockAvailable": 0,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.STL",
                        "active": true
                    })
                }
            });
            expect(componentAsAny.remainingTime).toBe(3600)
            component.changePartCount()
            expect(component.state).toBe('IDLE')
            expect(componentAsAny.remainingTime).toBe(3600)
        });
        it('startBuild: start the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            const testPart: Part = new Part({
                id: "9cc43545-3221-4a24-bd0c-04b429d11df4",
                buildTime: 30
            })
            componentAsAny.node = new Machine({
                id: "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                isRunning: () => { return false }
            });
            componentAsAny.target = new Job({
                id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b",
                part: testPart
            })
            component.onDrop({
                dragData: {
                    "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                    part: new Part({
                        "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                        "label": "Covid-19 Key",
                        "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                        "material": "PLA",
                        "color": "BLANCO",
                        "weight": 5,
                        "buildTime": 60,
                        "cost": 0.65,
                        "price": 2.0,
                        "stockLevel": 3,
                        "stockAvailable": 0,
                        "imagePath": "https://ibb.co/3dGbsRh",
                        "modelPath": "pieza3.STL",
                        "active": true
                    })
                }
            });
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.startBuild();
            jasmine.clock().tick(500);
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(component.isRunning()).toBeTrue();
            jasmine.clock().uninstall()
        });
        it('onClear: cancel/clear the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            componentAsAny.node = new Machine({
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
                "currentPartInstances": 1,
                "jobInstallmentDate": "2020-06-01T22:05:00Z"
            })
            const testPart: Part = new Part({
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
            componentAsAny.target = new Job({
                id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b",
                part: testPart
            })
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.onClear();
            jasmine.clock().tick(500);
            // expect(componentAsAny.sessionTimer.deactivate).toHaveBeenCalled()
            expect(component.node).toBeDefined()
            expect(component.target).toBeUndefined('The clear should clear this reference')
            expect(component.isRunning()).toBeFalse();
            expect(componentAsAny.backendConnections.length).toBe(1);
            jasmine.clock().uninstall()
        });
        it('completeBuild: start the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.buildTimeTimer = TestBed.createComponent(V1BuildCountdownTimerPanelComponent).componentInstance
            const testPart: Part = new Part({
                id: "9cc43545-3221-4a24-bd0c-04b429d11df4",
                buildTime: 30
            })
            componentAsAny.node = new Machine({
                id: "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                currentJobPartId: new Part(),
                buildTime: TEST_TIME,
                jobInstallmentDate: Date.now().toString,
                buildRecord: {
                    part: testPart,
                    remainingTime: 30
                },
                isRunning: () => { return true }
            });
            componentAsAny.target = new Job({
                id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b",
                part: testPart
            })
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.completeBuild();
            jasmine.clock().tick(500);
            expect(component.node).toBeDefined()
            expect(component.target).toBeUndefined('The clear should clear this reference')
            expect(component.isRunning()).toBeFalse();
            expect(componentAsAny.backendConnections.length).toBe(1);
            jasmine.clock().uninstall()
        });
    });
});
