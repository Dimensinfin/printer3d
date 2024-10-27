// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
// - TESTING
import { waitForAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { Extraction } from '@domain/extraction/Extraction.domain'
// - DOMAIN
import { V1ExtractionComponent } from './v1-extraction.component'

describe('COMPONENT V1ProjectRenderComponent [Module: COMMON]', () => {
    const extraction = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
    let component: V1ExtractionComponent

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                V1ExtractionComponent
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1ExtractionComponent)
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
        it('getUniqueId.success: check the extractor identifier', () => {
            component.node = extraction
            expect(component).toBeDefined()
            expect(component.getUniqueId()).toBeDefined()
            expect(component.getUniqueId()).toBe("-ID-")
        })
        it('getUniqueId.failure: check the extractor identifier when no node defined', () => {
            expect(component).toBeDefined()
            expect(component.getUniqueId()).toBe("-")
        })
        it('getLabel.success: check the extractor label', () => {
            component.node = extraction
            expect(component).toBeDefined()
            expect(component.getLabel()).toBeDefined()
            expect(component.getLabel()).toBe("-LABEL-")
        })
        it('getLabel.failure: check the extractor label when no node defined', () => {
            expect(component).toBeDefined()
            expect(component.getLabel()).toBe("-")
        })
        it('performExtraction.success: check the extractor link', () => {
            component.node = extraction
            expect(component).toBeDefined()
            expect(component.performExtraction()).toBe("-LINK-")
        })
        it('performExtraction.failure: check the extractor link when no node defined', () => {
            expect(component).toBeDefined()
            expect(component.performExtraction()).toBe("/extractions")
        })
    })
})
