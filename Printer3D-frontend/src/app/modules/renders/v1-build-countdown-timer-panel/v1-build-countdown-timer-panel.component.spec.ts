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
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { Machine } from '@domain/Machine.domain';
import { Part } from '@domain/Part.domain';
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel.component';
import { V3MachineRenderComponent } from '../v3-machine-render/v3-machine-render.component';
// import { V2MachineRenderComponent } from '../v2-machine-render/v2-machine-render.component';

const TEST_TIME: number = 1 * 3600 + 12 * 60;

describe('COMPONENT V1BuildCountdownTimerPanelComponent [Module: SHARED]', () => {
    let component: V1BuildCountdownTimerPanelComponent;
    // let machineRender = { isAutostart: () => { return 12 * 60; } };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1BuildCountdownTimerPanelComponent,
                V3MachineRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1BuildCountdownTimerPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.parent).toBeUndefined();
            expect(componentAsAny.time).toBeUndefined();
            expect(component.hours).toBe(0);
            expect(component.minutes).toBeUndefined();
            expect(componentAsAny.duration).toBeUndefined();
            expect(componentAsAny.timerSubscription).toBeUndefined();
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            await component.ngOnInit();
            expect(component).toBeDefined();
        });
        it('ngOnInit.notimer: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.parent = { isAutostart: () => { return false; } }
            spyOn(componentAsAny.parent, 'isAutostart')
            await component.ngOnInit();
            expect(component).toBeDefined();
            expect(componentAsAny.parent.isAutostart).toHaveBeenCalled();
            expect(componentAsAny.duration).toBeUndefined();
        });
        it('ngOnInit.timer: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.parent = { isAutostart: () => { return false; } }
            spyOn(componentAsAny.parent, 'isAutostart')
            component.time = TEST_TIME
            await component.ngOnInit();
            expect(component).toBeDefined();
            expect(componentAsAny.parent.isAutostart).toHaveBeenCalled();
            expect(componentAsAny.duration).toBe(TEST_TIME)
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12)
        });
        it('ngOnInit.autostart: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.parent = { isAutostart: () => { return true; } }
            component.time = TEST_TIME
            await component.ngOnInit();
            expect(component).toBeDefined();
            expect(componentAsAny.duration).toBe(TEST_TIME)
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12)
            expect(componentAsAny.timerSubscription).toBeDefined()
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy.empty: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
        });
        it('ngOnDestroy.building: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            componentAsAny.activate(100);
            expect(componentAsAny.timerSubscription).toBeDefined();
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('setTimer: set the timer fields', () => {
            const componentAsAny = component as any;
            componentAsAny.time = TEST_TIME;
            component.ngOnInit();
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12);
        });
        it('setTime: set the timer fields', () => {
            const componentAsAny = component as any;
            // componentAsAny.time = TEST_TIME;
            component.setTime(TEST_TIME);
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12);
        });
        it('activate.timer: start the timer', async () => {
            const TEST_TIME: number = 12 * 60;
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            await componentAsAny.activate(TEST_TIME);
            expect(componentAsAny.timerSubscription).toBeDefined()
        });
        it('deactivate: start the timer', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            componentAsAny.activate(100);
            expect(componentAsAny.timerSubscription).toBeDefined();
            component.deactivate();
            expect(componentAsAny.timerSubscription).toBeDefined();
        });
        it('completeTimer: complete the timer', fakeAsync(() => {
            const TEST_TIME: number = 1;
            const componentAsAny = component as any;
            componentAsAny.duration = TEST_TIME;
            componentAsAny.parent = { completeTime: () => { } }
            spyOn(componentAsAny.parent, 'completeTime')
            component.activate();
            tick(2000);
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.hours).toBe(0)
            expect(component.minutes).toBe(0);
             expect(componentAsAny.parent.completeTime).toHaveBeenCalled();
        }));
    });
});
