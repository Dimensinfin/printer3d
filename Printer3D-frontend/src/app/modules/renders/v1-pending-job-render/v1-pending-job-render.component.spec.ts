// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../platform/platform-constants';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
// - TESTING
import { TestBed } from '@angular/core/testing';
import { async } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
// - DOMAIN
import { V1PendingJobRenderComponent } from './v1-pending-job-render.component';
import { Job } from '@domain/production/Job.domain';
import { Part } from '@domain/inventory/Part.domain';

xdescribe('COMPONENT V1PendingJobRenderComponent [Module: RENDER]', () => {
    let component: V1PendingJobRenderComponent;
    let testPart: Part = new Part({
        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
        "label": "Boquilla Ganesha",
        "description": "Boquilla Ganesha",
        "material": "PLA",
        "color": "GRIS",
        "buildTime": 90,
        "cost": 1.0,
        "price": 6.0,
        "stockLevel": 5,
        "stockAvailable": 0,
        "imagePath": null,
        "modelPath": null,
        "active": true
    })
    let testJob: Job = new Job({
        id: "9903926b-e786-4fb2-8e8e-68960ebebb7a",
        part: testPart,
        priority: 3,
        partCount: 4,
        aggregatedCount: 8
    })

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1PendingJobRenderComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1PendingJobRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getUniqueId.success: check the "label" field when defined', () => {
            const componentAsAny = component as any
            componentAsAny.node = testJob;
            expect(component.getUniqueId()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        // });
        // it('getJob.success: access the node instance of this component', () => {
            componentAsAny.node = testJob;
            expect(component.getNode()).toBeDefined()
            expect(component.getNode().getId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        // });
        // it('getLabel.success: get contained Part "label" field', () => {
            componentAsAny.node = testJob;
            expect(component.getLabel()).toBeDefined()
            expect(component.getLabel()).toBe("Boquilla Ganesha")
        // });
        // it('getMaterial.success: get contained Part "material" field', () => {
            componentAsAny.node = testJob;
            expect(component.getMaterial()).toBeDefined()
            expect(component.getMaterial()).toBe("PLA")
        // });
        // it('getColor.success: get contained Part "color" field', () => {
            componentAsAny.node = testJob;
            expect(component.getColor()).toBeDefined()
            expect(component.getColor()).toBe("GRIS")
        // });
        // it('getBuildTime.success: get contained Part "buildTime" field', () => {
            componentAsAny.node = testJob;
            expect(component.getBuildTime()).toBeDefined()
            expect(component.getBuildTime()).toBe(90)
        // });
        // it('getCopies.success: get number of part copies', () => {
            componentAsAny.node = testJob;
            expect(component.getCopies()).toBe('x 4')
        // });
        // it('getPriority.success: get job priority', () => {
            componentAsAny.node = testJob;
            expect(component.getPriority()).toBe(3)
        // });
        // it('getAggregatedNumber.success: get the number of parts aggregates to the stack', () => {
            componentAsAny.node = testJob;
            expect(component.getAggregatedNumber()).toBe('x 8')
        });
        it('isEditable.success: true if the number of aggregated is editable', () => {
            const componentAsAny = component as any
            componentAsAny.node = testJob;
            componentAsAny.machine = {
                state: 'IDLE',
                isRunning: () => { return false }
            }
            expect(component.isEditable()).toBeTrue()
            componentAsAny.machine = {
                state: 'RUNNING',
                isRunning: () => { return true }
            }
            expect(component.isEditable()).toBeFalse()
            componentAsAny.machine = {
                state: 'COMPLETED',
                isRunning: () => { return false }
            }
            expect(component.isEditable()).toBeFalse()
            componentAsAny.machine = {
                state: 'IDLE',
                isRunning: () => { return false }
            }
            expect(component.isEditable()).toBeTrue()
        });
        it('isRunning.success: true if the number of aggregated is editable', () => {
            const componentAsAny = component as any
            componentAsAny.node = testJob;
            componentAsAny.machine = {
                state: 'RUNNING',
                isRunning: () => { return true }
            }
            expect(component.isRunning()).toBeTrue()
            componentAsAny.machine = {
                state: 'IDLE',
                isRunning: () => { return false }
            }
            expect(component.isRunning()).toBeFalse()
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Interactions]', () => {
        it('onMouseLeave.: send the event to recalculate the timer', () => {
            const componentAsAny = component as any
            let counter: number = 0
            componentAsAny.node = testJob;
            componentAsAny.machine = {
                state: 'IDLE',
                changePartCount: (count) => { counter = count }
            }
            component.onMouseLeave()
            expect(counter).toBe(4)
        });
    });
});
