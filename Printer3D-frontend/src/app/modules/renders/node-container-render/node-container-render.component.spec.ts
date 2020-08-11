// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { Node } from '@domain/Node.domain';
import { NodeContainerRenderComponent } from './node-container-render.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';

xdescribe('PANEL NodeContainerRenderComponent [Module: RENDER]', () => {
    let component: NodeContainerRenderComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                NodeContainerRenderComponent
            ],
            providers: [
            ]
        })
            .compileComponents();
        const fixture = TestBed.createComponent(NodeContainerRenderComponent);
        component = fixture.componentInstance;
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            expect(component.container).toBeUndefined()
            expect(component.node).toBeUndefined()
            expect(component.variant).toBe(EVariant.DEFAULT);
            expect(component.colorScheme).toBe('panel-white')
            expect(component.index).toBe(1)
            expect(component.selectOnHover).toBeFalse()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getNode.success: check the node field', () => {
            expect(component.node).toBeUndefined()
            component.node = new Node()
            expect(component.node).toBeDefined()
            expect(component.getNode()).toBeDefined()
        });
        it('getVariant.success: check the variant field', () => {
            const expected = EVariant.CATALOG
            component.variant = expected;
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
        it('getVariant.success: check the variant field', () => {
            const expected = EVariant.CATALOG
            component.variant = expected;
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [actions]', () => {
        it('mouseEnter.notauto: pass the hovered node to the parent view container', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                enterSelected: () => { }
            }
            spyOn(componentAsAny.container, 'enterSelected')
            component.mouseEnter(new Node())
            expect(componentAsAny.container.enterSelected).not.toHaveBeenCalled()
        });
        it('mouseEnter.auto: pass the hovered node to the parent view container', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                enterSelected: () => { }
            }
            component.selectOnHover = true
            spyOn(componentAsAny.container, 'enterSelected')
            component.mouseEnter(new Node())
            expect(componentAsAny.container.enterSelected).toHaveBeenCalled()
        });
        it('toggleExpanded.notExpandable: toggle the expand property for the node', () => {
            const componentAsAny = component as any;
            component.node = new Node({ expanded: true });
            expect(component.getNode().isExpanded()).toBeTrue();
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            spyOn(componentAsAny.container, 'notifyDataChanged')
            component.toggleExpanded()
            expect(component.getNode().isExpanded()).toBeTrue();
            expect(componentAsAny.container.notifyDataChanged).not.toHaveBeenCalled()
        });
        it('toggleExpanded.success: toggle the expand property for the node', () => {
            const componentAsAny = component as any;
            component.node = new Node({
                expanded: true,
                isExpandable: () => {
                    return true
                }
            });
            expect(component.getNode().isExpanded()).toBeTrue();
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            spyOn(componentAsAny.container, 'notifyDataChanged')
            component.toggleExpanded()
            expect(component.getNode().isExpanded()).toBeFalse();
            expect(componentAsAny.container.notifyDataChanged).toHaveBeenCalled()
        });
        it('toggleExpanded.failure: toggle the expand property for the node', () => {
            const node = new Node();
            expect(component.getNode()).toBeUndefined();
            component.toggleExpanded();
            expect(component.getNode()).toBeUndefined();
        });
        it('isExpanded.notexpandable: get the node expand state', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            component.node = new Node({
                isExpandable: () => { return false }
            })
            expect(component.isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(component.isExpanded()).toBeFalsy();
        });
        it('isExpanded.success: get the node expand state', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            component.node = new Node({
                isExpandable: () => { return true }
            })
            expect(component.isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(component.isExpanded()).toBeTrue();
        });
        it('isExpanded.failure: get the node expand state', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            expect(component.isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(component.isExpanded()).toBeFalsy();
        });
        it('isActive.success: get the node expand state', () => {
            const componentAsAny = component as any;
            componentAsAny.container = {
                notifyDataChanged: () => { }
            }
            component.node = new Node()
            expect(component.isActive()).toBeTrue();
            component.node = new Node({ active: false })
            expect(component.isActive()).toBeTrue();
        });
        it('isActive.failure: get the node expand state', () => {
            const componentAsAny = component as any;
            componentAsAny.container = { notifyDataChanged: () => { } }
            expect(component.isActive()).toBeTrue();
            component.toggleExpanded();
            expect(component.isActive()).toBeTrue();
        });
    });
});
