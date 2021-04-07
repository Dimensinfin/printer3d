// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel.component';
import { V3MachineRenderComponent } from '../v3-machine-render/v3-machine-render.component';

const TEST_TIME: number = 1 * 3600 + 12 * 60;

xdescribe('COMPONENT V1BuildCountdownTimerPanelComponent [Module: SHARED]', () => {
    let component: V1BuildCountdownTimerPanelComponent;

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
            expect(componentAsAny.show).toBeFalse()
            expect(component.hours).toBe(0);
            expect(component.minutes).toBe(0);
            expect(componentAsAny.duration).toBe(0);
            expect(componentAsAny.timerSubscription).toBeUndefined();
            expect(componentAsAny.lastMinute).toBeFalse()
            expect(componentAsAny.timerCompleted).toBeFalse()
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy.empty: validate destruction flow', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.ngOnDestroy()
            expect(componentAsAny.timerSubscription).toBeUndefined()
        });
        it('ngOnDestroy.building: validate destruction flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(100)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(componentAsAny.timerSubscription).toBeDefined();
            component.ngOnDestroy()
            expect(componentAsAny.timerSubscription).toBeDefined() // But unsubscribed
            jasmine.clock().uninstall()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('isLastMinute: true when on the last build minute', async () => {
            jasmine.clock().install();
            expect(component.isLastMinute()).toBeFalse()
            component.setTime(55)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(component.isLastMinute()).toBeTrue()
            jasmine.clock().uninstall()
        });
        it('isCompleted: timer is completed', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.parent = {
                completeTime: () => { }
            }
            expect(component.isCompleted()).toBeFalse()
            component.setTime(2)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(component.isCompleted()).toBeFalse()
            jasmine.clock().tick(3200);
            expect(component.isCompleted()).toBeTrue()
            jasmine.clock().uninstall()
        });
        it('setTime: set the timer fields', () => {
            const componentAsAny = component as any;
            component.setTime(TEST_TIME);
            expect(componentAsAny.duration).toBe(TEST_TIME);
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12);
        });
        it('activate.timer: start the timer', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            componentAsAny.parent = {
                completeTime: () => { }
            }
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(0)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(2)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(componentAsAny.timerSubscription).toBeDefined()
            component.setTime(100)
            await component.activate();
            jasmine.clock().tick(1300);
            expect(componentAsAny.timerSubscription).toBeDefined()
            jasmine.clock().uninstall()
        });
        it('deactivate: stop the timer', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            component.deactivate();
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(100)
            await component.activate();
            expect(componentAsAny.timerSubscription).toBeDefined();
            component.deactivate();
            expect(componentAsAny.timerSubscription).toBeDefined();
            jasmine.clock().uninstall()
        });
    });
});
