// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { Machine } from '@domain/Machine.domain';
import { Part } from '@domain/Part.domain';
import { TIMEOUT } from 'dns';
import { V3MachineRenderComponent } from './v3-machine-render.component';
import { Job } from '@domain/Job.domain';

const TEST_TIME: number = 12 * 60;

describe('COMPONENT V3MachineRenderComponent [Module: SHARED]', () => {
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
            expect(componentAsAny.sessionTimer).toBeUndefined();
            expect(componentAsAny.node).toBeUndefined();
            expect(component.self).toBeDefined()
            expect(componentAsAny.target).toBeUndefined();
            expect(componentAsAny.state).toBe('IDLE');
            expect(componentAsAny.remainingTime).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            await component.ngOnInit();
            const componentAsAny = component as any;
            expect(component.self).toBeDefined();
        });
        it('ngOnInit.idle: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine();
            await component.ngOnInit();
            expect(component.self).toBeDefined();
            expect(component.isRunning()).toBeFalse();
        });
        it('ngOnInit.running: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({
                currentJobPartId: new Part(),
                buildTime: TEST_TIME,
                jobInstallmentDate: Date.now().toString,
                buildRecord: {
                    part: new Part(),
                    remainingTime: 587
                },
                isRunning: () => { return true }
            });
            await component.ngOnInit();
            jasmine.clock().tick(500);
            expect(component.self).toBeDefined();
            expect(componentAsAny.node).toBeDefined();
            expect(componentAsAny.node.currentJobPartId).toBeDefined();
            expect(component.target).toBeDefined();
            expect(component.isRunning()).toBeTrue();
            expect(component.getBuildTime()).toBe(587);
            jasmine.clock().uninstall()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('isAutostart.false: true if the part is loaded from a running machine', () => {
            expect(component.isAutostart()).toBeFalse();
        });
        it('isAutostart:true true if the part is loaded from a running machine', () => {
            const componentAsAny = component as any;
            componentAsAny.state = 'RUNNING'
            expect(component.isAutostart()).toBeTrue();
        });
        it('getBuildTime: get the time left to build', async () => {
            expect(component.getBuildTime()).toBe(0);
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({
                currentJobPartId: new Part(),
                buildTime: TEST_TIME,
                jobInstallmentDate: Date.now().toString,
                buildRecord: {
                    part: new Part(),
                    remainingTime: 876
                },
                isRunning: () => { return true }
            });
            await component.ngOnInit()
            expect(component.getBuildTime()).toBe(876);
        });
        it('onDrop.empty: drop an empty element', () => {
            component.onDrop(null);
            expect(component.target).toBeUndefined();
        });
        it('onDrop.job: drop an empty element', () => {
            expect(component.target).toBeUndefined()
            expect(component.getBuildTime()).toBe(0);
            component.onDrop({
                dragData: {
                    part: new Part({
                        buildTime: 97
                    })
                }
            });
            expect(component.target).toBeDefined();
            expect(component.getBuildTime()).toBe(97 * 60);
        });
        it('getUniqueId: get the machine unique identifier for html identification', () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({
                id: "9903926b-e786-4fb2-8e8e-68960ebebb7a"
            });
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        });
        it('completeTime: singnal from the timer that the time has completed', () => {
            const componentAsAny = component as any;
            expect(component.state).toBe('IDLE')
            component.completeTime()
            expect(component.state).toBe('COMPLETED')
        });
        it('startBuild: start the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.sessionTimer = {
                activate: (seconds: number) => {
                    expect(seconds).toBe(30 * 60)
                }
            }
            spyOn(componentAsAny.sessionTimer, 'activate')
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
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.startBuild();
            jasmine.clock().tick(500);
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(componentAsAny.sessionTimer.activate).toHaveBeenCalled()
            expect(component.isRunning()).toBeTrue();
            jasmine.clock().uninstall()
        });
        it('onClear: cancel/clear the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.sessionTimer = {
                deactivate: () => { }
            }
            spyOn(componentAsAny.sessionTimer, 'deactivate')
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
            expect(componentAsAny.sessionTimer.deactivate).toHaveBeenCalled()
            expect(component.node).toBeDefined()
            expect(component.target).toBeUndefined('The clear should clear this reference')
            expect(component.isRunning()).toBeFalse();
            expect(componentAsAny.backendConnections.length).toBe(1);
            jasmine.clock().uninstall()
        });
        it('completeBuild: start the job', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.sessionTimer = {
                deactivate: () => { }
            }
            spyOn(componentAsAny.sessionTimer, 'deactivate')
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
