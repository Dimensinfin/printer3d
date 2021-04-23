// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - DOMAIN
import { Project } from '@domain/inventory/Project.domain'
import { Model } from '@domain/inventory/Model.domain'
import { PartContainer } from '@domain/inventory/PartContainer.domain'
import { V1OpenRequestsPageComponent } from './v1-open-requests-page.component'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'

describe('COMPONENT V1OpenRequestsPageComponent [Module: PRODUCTION]', () => {
    let component: V1OpenRequestsPageComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1OpenRequestsPageComponent
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1OpenRequestsPageComponent)
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
            expect(componentAsAny.openRequestsPanel).toBeUndefined()
            expect(componentAsAny.selectedRequestPanel).toBeUndefined()
            expect(component.self).toBeDefined()
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('selectRequest: update the selected customer request', () => {
            const componentAsAny = component as any
            componentAsAny.selectedRequestPanel = { selectRequest: () => { } }
            spyOn(componentAsAny.selectedRequestPanel, 'selectRequest')
            component.selectRequest(new CustomerRequest())
            expect(componentAsAny.selectedRequestPanel.selectRequest).toHaveBeenCalled()
        })
        it('refresh: check that the refresh is passed to all panels', () => {
            const componentAsAny = component as any
            componentAsAny.openRequestsPanel = { refresh: () => { } }
            componentAsAny.selectedRequestPanel = { cleanSelectedRequest: () => { } }
            spyOn(componentAsAny.openRequestsPanel, 'refresh')
            spyOn(componentAsAny.selectedRequestPanel, 'cleanSelectedRequest')
            component.refresh()
            expect(componentAsAny.openRequestsPanel.refresh).toHaveBeenCalled()
            expect(componentAsAny.selectedRequestPanel.cleanSelectedRequest).toHaveBeenCalled()
        })
    })
})
