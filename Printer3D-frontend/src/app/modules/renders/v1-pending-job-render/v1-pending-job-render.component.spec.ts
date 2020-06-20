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
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/app-store.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { V1PendingJobRenderComponent } from './v1-pending-job-render.component';
import { Job } from '@domain/Job.domain';
import { Part } from '@domain/Part.domain';

describe('COMPONENT V1PendingJobRenderComponent [Module: RENDER]', () => {
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
        part: testPart
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
            component.node = testJob;
            expect(component.getUniqueId()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        });
        it('getJob.success: access the node instance of this component', () => {
            component.node = testJob;
            expect(component.getJob()).toBeDefined()
            expect(component.getJob().getId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        });
        it('getLabel.success: get contained Part "label" field', () => {
            component.node = testJob;
            expect(component.getLabel()).toBeDefined()
            expect(component.getLabel()).toBe("Boquilla Ganesha")
        });
        it('getMaterial.success: get contained Part "material" field', () => {
            component.node = testJob;
            expect(component.getMaterial()).toBeDefined()
            expect(component.getMaterial()).toBe("PLA")
        });
        it('getColor.success: get contained Part "color" field', () => {
            component.node = testJob;
            expect(component.getColor()).toBeDefined()
            expect(component.getColor()).toBe("GRIS")
        });
        it('getBuildTime.success: get contained Part "buildTime" field', () => {
            component.node = testJob;
            expect(component.getBuildTime()).toBeDefined()
            expect(component.getBuildTime()).toBe(90)
        });
    });
});
