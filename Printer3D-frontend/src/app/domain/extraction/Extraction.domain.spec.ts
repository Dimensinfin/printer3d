// - DOMAIN
import { Extraction } from "./Extraction.domain"

describe('CLASS Extraction [Module: EXTRACTION]', () => {
    beforeEach(() => {
    })

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Extraction()
            const instanceAsAy = instance as any
            expect(instance).toBeDefined()
            expect(instanceAsAy.id).toBeUndefined()
            expect(instanceAsAy.label).toBeUndefined()
            expect(instanceAsAy.link).toBeUndefined()
        })
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
            const instanceAsAy = instance as any
            expect(instance).toBeDefined()
            expect(instanceAsAy.id).toBe('-ID-')
            expect(instanceAsAy.label).toBe('-LABEL-')
            expect(instanceAsAy.link).toBe('-LINK-')
        })
    })

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getUniqueId: obtain the unique identifier value', () => {
            const instance = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
            expect(instance).toBeDefined()
            expect(instance.getUniqueId()).toBe('-ID-')
        })
        it('getLabel: get the extraction button label', () => {
            const instance = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
            expect(instance).toBeDefined()
            expect(instance.getLabel()).toBe('-LABEL-')
        })
        it('getLink: get the extraction destination link', () => {
            const instance = new Extraction({ id: '-ID-', label: '-LABEL-', link: '-LINK-' })
            expect(instance).toBeDefined()
            expect(instance.getLink()).toBe('-LINK-')
        })
    })
})
