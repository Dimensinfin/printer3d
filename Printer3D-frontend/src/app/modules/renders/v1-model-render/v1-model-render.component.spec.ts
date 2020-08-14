// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { V1ModelRenderComponent } from './v1-model-render.component';
import { Model } from '@domain/inventory/Model.domain';
import { PartStack } from '@domain/PartStack.domain';

describe('COMPONENT V1ModelRenderComponent [Module: RENDER]', () => {
    let component: V1ModelRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1ModelRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1ModelRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.');
            expect(component.inside).toBeFalse()
            expect(componentAsAny.editing).toBeFalse()
            expect(componentAsAny.nodeBackup).toBeUndefined()
        });
    });
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters: validate getter contract', () => {
            const model = new Model({
                id: "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                label: '-TEST-LABEL-',
                price: 4,
                stockLeve: 4,
                active: true,
                imagePath: 'http://igg.go',
                partList: [
                    new PartStack({
                        stackCount: 3
                    })
                ]
            })
            // const modelAsAny = model as any
            component.node = model
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getContentCount()).toBe(3)
            expect(component.getPrice()).toBe('4 €')
            expect(component.getComposingParts()).toBeDefined()
            expect(component.getComposingParts().length).toBe(1)
            expect(component.isActive()).toBeTrue()
        });
    });
    describe('Coverage Phase [Interactions]', () => {
        it('mouseEnter: control the mouse enter/exit', () => {
            expect(component.inside).toBeFalse()
            component.mouseEnter({})
            expect(component.inside).toBeTrue()
            component.mouseLeave({})
            expect(component.inside).toBeFalse()
        });
        it('mouseLeave: control the mouse enter/exit', () => {
            expect(component.inside).toBeFalse()
            component.mouseEnter({})
            expect(component.inside).toBeTrue()
            component.mouseLeave({})
            expect(component.inside).toBeFalse()
        });
        it('toggleDisplay: change the diplay mode', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.inside).toBeFalse()
            component.toggleDisplay()
            expect(componentAsAny.inside).toBeTrue()
            component.toggleDisplay()
            expect(componentAsAny.inside).toBeFalse()
        });
        it('toggleEdition: change the model edit state', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                addSelection: () => { }
            }
            spyOn(componentAsAny.container, 'addSelection')
            expect(componentAsAny.editing).toBeFalse()
            component.toggleEdition()
            expect(componentAsAny.editing).toBeTrue()
            expect(componentAsAny.container.addSelection).toHaveBeenCalledTimes(1)
            component.toggleEdition()
            expect(component.inside).toBeFalse()
            expect(componentAsAny.container.addSelection).toHaveBeenCalledTimes(2)
        });
    });
});
