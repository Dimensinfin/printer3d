// - DOMAIN
import { Model } from './Model.domain'
import { Part } from './Part.domain'
import { PartContainer } from './PartContainer.domain'
import { Project } from './Project.domain'

describe('CLASS Project [Module: DOMAIN]', () => {
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const instance = new Project()
            const instanceAsAny = instance as any
            expect(instance).toBeDefined()
            expect(instanceAsAny.name).toBeUndefined()
            expect(instanceAsAny.contents).toBeDefined()
            expect(instanceAsAny.contents.length).toBe(0)
        })
        it('constructor.object: validate initial state with object data', () => {
            const instance = new Project({
                name: '-PROJECT-'
            })
            instance.addContainer(new Model())
            instance.addContainer(new Model())
            const instanceAsAny = instance as any
            expect(instance).toBeDefined()
            expect(instanceAsAny.name).toBe('-PROJECT-')
            expect(instanceAsAny.contents).toBeDefined()
            expect(instanceAsAny.contents.length).toBe(2)
        })
    })

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getName: validate the name field values', () => {
            let instance = new Project({ name: '-PROJECT-' })
            expect(instance.getName()).toBe('-PROJECT-')
        })
        it('getContents: validate the contents count', () => {
            let instance = new Project({ name: '-PROJECT-' })
            expect(instance.getContents().length).toBe(0)
            instance.addContainer(new Model())
            expect(instance.getContents().length).toBe(1)
        })
    })
    describe('Coverage Phase [Methods]', () => {
        it('isActive: part containers are always active', () => {
            let instance = new Project({ name: '-PROJECT-' })
            expect(instance.isActive()).toBeTrue()
        })
        it('isExpandable: is ever expnadable', () => {
            let instance = new Project({ name: '-PROJECT-' })
            expect(instance.isExpandable()).toBeTrue()
        })
        it('addContainer: check the code to add a new Model', () => {
            const instance = new Project({ name: '-PROJECT-' })
            const instanceAsAny = instance as any
            expect(instanceAsAny.contents).toBeDefined()
            expect(instanceAsAny.contents.length).toBe(0)
            instance.addContainer(new Model())
            expect(instanceAsAny.contents.length).toBe(1)
        })
    })
})
