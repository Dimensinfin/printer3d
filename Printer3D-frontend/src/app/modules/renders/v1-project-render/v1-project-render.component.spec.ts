// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - DOMAIN
import { V1ProjectRenderComponent } from './v1-project-render.component'
import { Project } from '@domain/inventory/Project.domain'
import { Model } from '@domain/inventory/Model.domain'
import { PartContainer } from '@domain/inventory/PartContainer.domain'

fdescribe('COMPONENT V1ProjectRenderComponent [Module: RENDER]', () => {
    const project = new Project({
        "name": "-PROJECT-NAME-"
    })
    let component: V1ProjectRenderComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1ProjectRenderComponent
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1ProjectRenderComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getNode.success: check the return type for the underlying node', () => {
            component.node = project
            expect(component).toBeDefined()
            expect(component.getNode()).toBeDefined()
            expect(component.getNode() instanceof Project).toBeTrue()
            expect(component.getProjectName()).toBe("-PROJECT-NAME-")
        })
        it('getNode.failure: check the return type for the underlying node', () => {
            expect(component.getNode()).toBeUndefined()
        })
        it('getUniqueId.success: check the value for the unique identifier', () => {
            component.node = project
            expect(component.getUniqueId()).toBe("-PROJECT-NAME-")
        })
        it('getUniqueId.failure: check the value for the unique identifier', () => {
            expect(component.getUniqueId()).toBe('-')
        })
        it('getProjectName.success: check the value for the project name', () => {
            component.node = project
            expect(component.getProjectName()).toBe("-PROJECT-NAME-")
        })
        it('getProjectName.failure: check the default value for the project name', () => {
            expect(component.getProjectName()).toBe('-')
        })
        it('isExpanded.collapsed: check the expansion state when inited', () => {
            expect(component.isExpanded()).toBeFalse()
            component.node = project
            expect(component.isExpanded()).toBeFalse()
            component.node.toggleExpanded()
            expect(component.isExpanded()).toBeTrue()
            component.node.toggleExpanded()
            expect(component.isExpanded()).toBeFalse()
        })
        it('getContainers.success: check the contents values', () => {
            project.addContainer(new Model())
            project.addContainer(new PartContainer())
            component.node = project
            expect(component.getContainers()).toBeDefined()
            expect(component.getContainers().length).toBe(2)
        })
        it('getContainers.failure: check the contents values for a not downloaded node', () => {
            expect(component.getContainers()).toBeDefined()
            expect(component.getContainers().length).toBe(0)
        })
    })
})
