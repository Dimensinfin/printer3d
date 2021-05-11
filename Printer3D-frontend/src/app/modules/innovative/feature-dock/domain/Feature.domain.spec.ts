// - TESTING
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { Feature } from '@bit/innovative.innovative-core.feature-dock/domain/Feature.domain'

describe('CLASS Feature [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService)
    })

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Feature()
            expect(instance).toBeDefined()
            expect(instance.label).toBeUndefined()
            expect(instance.active).toBeFalsy()
            expect(instance.route).toBeUndefined()
        })
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            expect(instance).toBeDefined()
            expect(instance.label).toBe('-TEST-LABEL-')
            expect(instance.active).toBeTruthy()
            expect(instance.route).toBe('-TEST-ROUTE-')
        })
    })

    // - G E T T E R   P H A S E
    describe('Getter Phase', () => {
        it('getRoute: access "route" field.', () => {
            const instance = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            expect(instance).toBeDefined()
            expect(instance.route).toBe('-TEST-ROUTE-')
            expect(instance.getRoute()).toBe('-TEST-ROUTE-')
        })
    })

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('equals.success: compare two Featured for equality.', () => {
            const instanceA = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            const instanceB = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            expect(instanceA).toBeDefined()
            expect(instanceB).toBeDefined()
            expect(instanceA.equals(instanceB)).toBeTruthy()
        })
        it('equals.failure: compare two Featured for equality.', () => {
            const instanceA = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            const instanceB = new Feature({ label: '-TEST-LABEL-NOT EQUAL-', active: true, route: '-TEST-ROUTE-' })
            expect(instanceA).toBeDefined()
            expect(instanceB).toBeDefined()
            expect(instanceA.equals(instanceB)).toBeFalse()
        })
        it('equals.failure: compare two Featured for equality.', () => {
            const instanceA = new Feature({ label: '-TEST-LABEL-', active: false, route: '-TEST-ROUTE-' })
            const instanceB = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            expect(instanceA).toBeDefined()
            expect(instanceB).toBeDefined()
            expect(instanceA.equals(instanceB)).toBeFalse()
        })
        it('equals.failure: compare two Featured for equality.', () => {
            const instanceA = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            const instanceB = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-NOT-EQUAL-' })
            expect(instanceA).toBeDefined()
            expect(instanceB).toBeDefined()
            expect(instanceA.equals(instanceB)).toBeFalse()
        })
        it('activate: activate the feature.', () => {
            const instance = new Feature({ label: '-TEST-LABEL-', active: false, route: '-TEST-ROUTE-' })
            expect(instance).toBeDefined()
            expect(instance.active).toBeFalsy()
            const obtained = instance.activate()
            expect(obtained).toBeFalsy()
            expect(instance.active).toBeTruthy()
        })
        it('deactivate: deactivate the feature.', () => {
            const instance = new Feature({ label: '-TEST-LABEL-', active: true, route: '-TEST-ROUTE-' })
            expect(instance).toBeDefined()
            expect(instance.active).toBeTrue()
            const obtained = instance.deactivate()
            expect(obtained).toBeTrue()
            expect(instance.active).toBeFalse()
        })
    })
})
