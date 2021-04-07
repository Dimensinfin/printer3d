// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { async, fakeAsync, tick } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
// - DOMAIN
import { V1PatchNoteRenderComponent } from './v1-patch-note-render.component'
import { Node } from '@domain/Node.domain'

describe('COMPONENT V1PatchNoteRenderComponent [Module: RENDER]', () => {
    let component: V1PatchNoteRenderComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1PatchNoteRenderComponent
            ]
        }).compileComponents()

        component = TestBed.createComponent(V1PatchNoteRenderComponent).componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getContent.success: get the patch notes content', () => {
            component.node = new Node({ getContent: () => { return '-CONTENT-' } })
            expect(component.getContent()).toBe('-CONTENT-')
        })
        it('getContent.undefined: get the patch notes content', () => {
            expect(component.getContent()).toBe('-')
        })
    })
})
