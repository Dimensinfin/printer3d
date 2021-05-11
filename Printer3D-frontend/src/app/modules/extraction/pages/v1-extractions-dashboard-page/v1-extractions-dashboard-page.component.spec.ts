// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { Extraction } from '@domain/extraction/Extraction.domain'
// - DOMAIN
import { V1ExtractionsDashboardPageComponent } from './v1-extractions-dashboard-page.component'

describe('COMPONENT V1ExtractionsDashboardPageComponent [Module: COMMON]', () => {
    const extraction = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
    let component: V1ExtractionsDashboardPageComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1ExtractionsDashboardPageComponent
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1ExtractionsDashboardPageComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(component.extractClosedRequests).toBeUndefined()
        })
    })
    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.filter true: validate initialization flow', fakeAsync(() => {
            component.ngOnInit()
            expect(component.extractClosedRequests).toBeDefined()
            expect(component.extractClosedRequests.getUniqueId()).toBe("001-closed-customer-requests")
        }))
    })
})
