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
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
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
import { V2MachineRenderComponent } from '../v2-machine-render/v2-machine-render.component';

const TEST_TIME: number = 12 * 60;

xdescribe('COMPONENT V1BuildCountdownTimerPanelComponent [Module: SHARED]', () => {
    let component: V1BuildCountdownTimerPanelComponent;
    let machineRender = { isAutostart: () => { return 12 * 60; } };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1BuildCountdownTimerPanelComponent,
                V2MachineRenderComponent
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
            expect(component.minutes).toBeUndefined();
            expect(component.seconds).toBeUndefined();
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
        it('ngOnInit.parent: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.parent = { isAutostart: () => { return false; } }
            spyOn(componentAsAny.parent, 'isAutostart')
            await component.ngOnInit();
            expect(component).toBeDefined();
            expect(componentAsAny.parent.isAutostart).toHaveBeenCalled();
            expect(componentAsAny.duration).toBeUndefined();
        });
        xit('ngOnInit.running: validate initialization flow', () => {
            const componentAsAny = component as any;
            componentAsAny.timer = TEST_TIME;
            componentAsAny.parent = { isAutostart: () => { return true; } }
            component.ngOnInit();
            expect(componentAsAny.timerSubscription).toBeDefined();
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.minutes).toBe(12);
            expect(component.seconds).toBe(0);
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
            expect(component.minutes).toBe(12);
            expect(component.seconds).toBe(0);
        });
        it('activate.timer: start the timer', async () => {
            const TEST_TIME: number = 12 * 60;
            const componentAsAny = component as any;
            await componentAsAny.activate(TEST_TIME);
            setTimeout(() => {
                expect(componentAsAny.duration).toBe(TEST_TIME);
                expect(component.minutes).toBe(12);
                expect(component.seconds).toBe(0);
            }, 5000);
        });
        it('activate.null: start the timer', async () => {
            const TEST_TIME: number = 80;
            const componentAsAny = component as any;
            componentAsAny.duration = TEST_TIME;
            await component.activate();
            setTimeout(() => {
                expect(componentAsAny.duration).toBe(TEST_TIME);
                expect(component.minutes).toBe(1);
                expect(component.seconds).toBe(20);
            }, 5000);
        });
        it('deactivate: start the timer', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            componentAsAny.activate(100);
            expect(componentAsAny.timerSubscription).toBeDefined();
            component.deactivate();
            expect(componentAsAny.timerSubscription).toBeDefined();
        });
        it('completeTimer: start the timer', fakeAsync(() => {
            const TEST_TIME: number = 1;
            const componentAsAny = component as any;
            componentAsAny.duration = TEST_TIME;
            spyOn(componentAsAny, 'completeTimer').and.callThrough()
            component.activate();
            tick(2000);
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.minutes).toBe(0);
            expect(component.seconds).toBe(0);
            expect(componentAsAny.completeTimer).toHaveBeenCalled();
        }));
    });
});
