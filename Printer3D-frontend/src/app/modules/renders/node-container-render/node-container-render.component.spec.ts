// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';

import { NodeContainerRenderComponent } from './node-container-render.component';
import { GroupContainer } from '@domain/GroupContainer.domain';
import { NeoCom } from '@domain/NeoCom.domain';
import { AppPanelComponent } from '../../panels/app-panel/app-panel.component';
import { Container } from '@angular/compiler/src/i18n/i18n_ast';
import { NeoComExpandable } from '@domain/NeoComExpandable.domain';
import { Fitting } from '@domain/Fitting.domain';

describe('RENDER NodeContainerRenderComponent [Module: SHARED]', () => {
    let fixture: ComponentFixture<NodeContainerRenderComponent>;
    let component: NodeContainerRenderComponent;
    let isolation: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            //     imports: [
            //         AppPanelComponent
            //    ],
            declarations: [
                NodeContainerRenderComponent,
                AppPanelComponent
            ],
        })
            .compileComponents();
        fixture = TestBed.createComponent(NodeContainerRenderComponent);
        isolation = TestBed.get(SupportIsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[renders/NodeContainerRenderComponent]> should be created');
            component = fixture.componentInstance;
            expect(component).toBeDefined('component has not been created.');
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [actions]', () => {
        it('mouseEnter: pass the hovered node to the parent view container', () => {
            const container = TestBed.createComponent(AppPanelComponent).componentInstance;
            component = fixture.componentInstance;
            component.container = container;
            let spy = spyOn(component.container, 'enterSelected');
            const node = new NeoCom();
            component.mouseEnter(node);
            expect(spy).toHaveBeenCalled();
        });
        it('toggleExpanded.success: toggle the expand property for the node', () => {
            const container = TestBed.createComponent(AppPanelComponent).componentInstance;
            component = fixture.componentInstance;
            component.container = container;
            let spy = spyOn(component.container, 'notifyDataChanged');
            const node = new NeoComExpandable();
            component.node = node;
            expect(component.getNode().isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(spy).toHaveBeenCalled();
            expect(component.getNode().isExpanded()).toBeTruthy();
        });
        it('toggleExpanded.failure: toggle the expand property for the node', () => {
            const node = new NeoCom();
            component = fixture.componentInstance;
            expect(component.getNode()).toBeUndefined();
            component.toggleExpanded();
            expect(component.getNode()).toBeUndefined();
        });
        it('isExpanded.success: get the node expand state', () => {
            const container = TestBed.createComponent(AppPanelComponent).componentInstance;
            component = fixture.componentInstance;
            component.container = container;
            let spy = spyOn(component.container, 'notifyDataChanged');
            const node = new NeoComExpandable();
            component.node = node;
            expect(component.isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(spy).toHaveBeenCalled();
            expect(component.isExpanded()).toBeTruthy();
        });
        it('isExpanded.failure: get the node expand state', () => {
            const container = TestBed.createComponent(AppPanelComponent).componentInstance;
            component = fixture.componentInstance;
            component.container = container;
            let spy = spyOn(component.container, 'notifyDataChanged');
            const node = new NeoComExpandable();
            expect(component.isExpanded()).toBeFalsy();
            component.toggleExpanded();
            expect(component.isExpanded()).toBeFalsy();
        });
        it('getColorSchemePanelStyle.standard: get class style to be applied to the panel', () => {
            component = fixture.componentInstance;
            const node = new Fitting();
            component.node = node;
            expect(node.isSelected()).toBeFalsy();
            expect(node.isExpanded()).toBeFalsy();
            let obtained = component.getColorSchemePanelStyle();
            expect(obtained).toBe('panel-green');
        });
        it('getColorSchemePanelStyle.expanded: get class style to be applied to the panel', () => {
            component = fixture.componentInstance;
            const node = new Fitting();
            node.expand();
            component.node = node;
            expect(node.isSelected()).toBeFalsy();
            expect(node.isExpanded()).toBeTruthy();
            let obtained = component.getColorSchemePanelStyle();
            expect(obtained).toBe('panel-green panel-green-expanded');
        });
        it('getColorSchemePanelStyle.selected: get class style to be applied to the panel', () => {
            component = fixture.componentInstance;
            const node = new Fitting();
            node.select();
            component.node = node;
           expect(node.isSelected()).toBeTruthy();
            expect(node.isExpanded()).toBeFalsy();
            let obtained = component.getColorSchemePanelStyle();
            expect(obtained).toBe('panel-green panel-green-selected');
        });
        it('getColorSchemePanelStyle.all: get class style to be applied to the panel', () => {
            component = fixture.componentInstance;
            const node = new Fitting();
            node.expand();
            node.select();
            component.node = node;
            expect(node.isSelected()).toBeTruthy();
            expect(node.isExpanded()).toBeTruthy();
            let obtained = component.getColorSchemePanelStyle();
            expect(obtained).toBe('panel-green panel-green-expanded panel-green-selected');
        });
    });

    describe('Code Coverage Phase [getters]', () => {
        it('getNode.success: obtain the contained node', () => {
            const expectedName = isolation.generateRandomString(32);
            const expected = new GroupContainer();
            expected.setTitle(expectedName);
            component = fixture.componentInstance;
            component.node = expected;
            const obtained = component.getNode() as GroupContainer;
            expect(obtained).toBeDefined();
            expect(obtained.getGroupTitle()).toBe(expectedName);
        });
    });
});
