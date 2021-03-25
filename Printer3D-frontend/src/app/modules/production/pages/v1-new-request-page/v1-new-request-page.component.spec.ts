// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - PROVIDERS
// - DOMAIN
import { V1NewRequestPageComponent } from './v1-new-request-page.component'

describe('COMPONENT V1NewRequestPageComponent [Module: INVENTORY]', () => {
    let component: V1NewRequestPageComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1NewRequestPageComponent
            ],
        }).compileComponents()

        const fixture = TestBed.createComponent(V1NewRequestPageComponent)
        component = fixture.componentInstance
     }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            const componentAsAny = component as any
            expect(componentAsAny.sellableElements).toBeUndefined()
        })
    })
    // - R E F R E S H   I N T E R F A C E
    describe('Refresh Interface Phase', () => {
        it('refresh.success: pass the refresh event to the child component', () => {
            const componentAsAny = component as any
            componentAsAny.sellableElements = { refresh: jasmine.createSpy('refresh') }
            component.refresh()
            expect(componentAsAny.sellableElements.refresh).toHaveBeenCalled()
        })
    })
})
