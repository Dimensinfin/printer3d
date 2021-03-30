// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - DOMAIN
import { V3InventoryPageComponent } from './v3-inventory-page.component'
import { Model } from '@domain/inventory/Model.domain'

describe('COMPONENT V3InventoryPageComponent [Module: RENDER]', () => {
    let component: V3InventoryPageComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V3InventoryPageComponent
            ]
        }).compileComponents()

        component = TestBed.createComponent(V3InventoryPageComponent).componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(componentAsAny.catalogPanel).toBeUndefined()
            expect(componentAsAny.modelEditingPanel).toBeUndefined()
            expect(component.selected).toBeUndefined()
            expect(component.self).toBeDefined('The self is set on the creation of the component.')
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Interactions]', () => {
        it('setSelected.success: check the setting of a Model on the selection', () => {
            expect(component.selected).toBeUndefined()
            component.setSelected(new Model())
            expect(component.selected).toBeDefined()
        })
        it('closeEditor.success: check the close of the Model editor', () => {
            const componentAsAny = component as any
            componentAsAny.modelEditingPanel = { stopEditing: () => { } }
            spyOn(componentAsAny.modelEditingPanel, 'stopEditing')
            expect(component.selected).toBeUndefined()
            component.closeEditor()
            expect(componentAsAny.modelEditingPanel.stopEditing).toHaveBeenCalled()
        })
    })
    describe('Code Coverage Phase [IRefreshable]', () => {
        it('clean.success: validate the execution of the IRefreshable interface methods', () => {
            component.clean()
            expect(component.self).toBeDefined('The self is set on the creation of the component.')
        })
        it('refresh.success: validate the execution of the IRefreshable interface methods', () => {
            const componentAsAny = component as any
            component.selected = new Model()
            expect(component.selected).toBeDefined()
            componentAsAny.catalogPanel = { refresh: () => { } }
            spyOn(componentAsAny.catalogPanel, 'refresh')
            component.refresh()
            expect(component.selected).toBeUndefined()
            expect(componentAsAny.catalogPanel.refresh).toHaveBeenCalled()
        })
    })
})
