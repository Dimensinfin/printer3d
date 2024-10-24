// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { discardPeriodicTasks, waitForAsync } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - DOMAIN
import { V1BuildCountdownTimerPanelComponent } from './v1-build-countdown-timer-panel.component'
import { V3MachineRenderComponent } from '../v3-machine-render/v3-machine-render.component'

const TEST_TIME: number = 1 * 3600 + 12 * 60

describe('COMPONENT V1BuildCountdownTimerPanelComponent [Module: SHARED]', () => {
    let component: V1BuildCountdownTimerPanelComponent

    beforeEach(waitForAsync(() => {
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
        }).compileComponents()

        const fixture = TestBed.createComponent(V1BuildCountdownTimerPanelComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(component).toBeDefined('component has not been created.')
            const componentAsAny = component as any
            expect(componentAsAny.parent).toBeUndefined()
            expect(componentAsAny.id).toBeUndefined()
            expect(componentAsAny.show).toBeFalse()
            expect(component.hours).toBe(0)
            expect(component.minutes).toBe(0)
            expect(componentAsAny.duration).toBe(0)
            expect(componentAsAny.timerSubscription).toBeUndefined()
            expect(componentAsAny.lastMinute).toBeFalse()
            expect(componentAsAny.timerCompleted).toBeFalse()
        })
    })

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy.empty: validate destruction flow', () => {
            const componentAsAny = component as any
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.ngOnDestroy()
            expect(componentAsAny.timerSubscription).toBeUndefined()
        })
        it('ngOnDestroy.building: validate destruction flow', fakeAsync(() => {
            const componentAsAny = component as any
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(100)
            component.activate()
            tick(1300)
            expect(componentAsAny.timerSubscription).toBeDefined()
            component.ngOnDestroy()
            expect(componentAsAny.timerSubscription).toBeDefined() // But unsubscribed
        }))
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getUniqueId: get the unique timer identifier', () => {
            const componentAsAny = component as any
            componentAsAny.id = '-IDENTIFIER-'
            expect(component.getUniqueId()).toBe('-IDENTIFIER-')
        })
    })
    describe('Code Coverage Phase [Methods]', () => {
        it('isLastMinute: true when on the last build minute', fakeAsync(() => {
            expect(component.isLastMinute()).toBe(false, 'Initial value should be false.')
            component.setTime(55)
            expect(component.hours).toBe(0)
            expect(component.minutes).toBe(0)
            component.activate()
            tick(300) // Advance 13 seconds
            expect(component.isLastMinute()).toBe(true, 'Time advanced to last minute.')
            discardPeriodicTasks()
        }))
        it('isCompleted: timer is completed', fakeAsync(() => {
            const componentAsAny = component as any
            componentAsAny.parent = {
                completeTime: () => { }
            }
            expect(component.isCompleted()).toBeFalse()
            component.setTime(2)
            component.activate()
            tick(1300)
            expect(component.isCompleted()).toBeFalse()
            tick(3200)
            expect(component.isCompleted()).toBeTrue()
            discardPeriodicTasks()
        }))
        it('setTime: set the timer fields', () => {
            const componentAsAny = component as any
            component.setTime(TEST_TIME)
            expect(componentAsAny.duration).toBe(TEST_TIME)
            expect(component.hours).toBe(1)
            expect(component.minutes).toBe(12)
        })
        it('activate.timer: start the timer', fakeAsync(() => {
            const componentAsAny = component as any
            componentAsAny.parent = {
                completeTime: () => { }
            }
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(0)
            component.activate()
            tick(300)
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(2)
            component.activate()
            tick(300)
            expect(componentAsAny.timerSubscription).toBeDefined()
            component.setTime(100)
            component.activate()
            tick(2000)
            expect(componentAsAny.timerSubscription).toBeDefined()
            discardPeriodicTasks()
        }))
        it('deactivate: stop the timer', fakeAsync(() => {
            const componentAsAny = component as any
            component.deactivate()
            tick(300)
            expect(componentAsAny.timerSubscription).toBeUndefined()
            component.setTime(100)
            component.activate()
            tick(300)
            expect(componentAsAny.timerSubscription).toBeDefined()
            component.deactivate()
            tick(300)
            expect(componentAsAny.timerSubscription).toBeDefined()
            discardPeriodicTasks()
        }))
    })
})
