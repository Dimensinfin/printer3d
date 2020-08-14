// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { BackgroundEnabledComponent } from '../background-enabled/background-enabled.component';
import { AppPanelComponent } from './app-panel.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { Node } from '@domain/Node.domain';

describe('PANEL AppPanelComponent [Module: CORE]', () => {
    let component: AppPanelComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                AppPanelComponent,
                BackgroundEnabledComponent
            ],
            providers: [
            ]
        })
            .compileComponents();
        const fixture = TestBed.createComponent(AppPanelComponent);
        component = fixture.componentInstance;
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.self).toBeDefined();
            expect(component.variant).toBe(EVariant.DEFAULT);
            expect(componentAsAny.downloading).toBeTrue();
            expect(componentAsAny.dataModelRoot).toBeDefined()
            expect(componentAsAny.dataModelRoot.length).toBe(0)
            expect(componentAsAny.renderNodeList).toBeDefined()
            expect(componentAsAny.renderNodeList.length).toBe(0)
            expect(componentAsAny.selection).toBeDefined()
            expect(componentAsAny.selection.getFirstSelected()).toBeUndefined()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getVariant.success: check the variant field', () => {
            const expected = EVariant.CATALOG
            component.variant = expected;
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
        it('isDownloading.success: check the downloading flag', () => {
            const expected = false;
            let componentAny = component as any;
            componentAny.downloading = expected;
            let obtained = component.isDownloading();
            expect(obtained).toBe(expected);
        });
        it('getNodes2Render.success: check the nodes to be rendered', () => {
            let componentAny = component as any;
            componentAny.renderNodeList.push(new Node());
            let obtained = component.getNodes2Render();
            expect(obtained).toBeDefined();
            expect(obtained.length).toBe(1);
        });
        it('startDownloading: signal the start for the downloading', () => {
            const componentAsAny = component as any;
            componentAsAny.downloading = false
            expect(component.isDownloading()).toBeFalse()
            component.startDownloading()
            expect(component.isDownloading()).toBeTrue()
        });
    });
    describe('Code Coverage Phase [setters]', () => {
        it('setVariant.success: validate the set for the variant', () => {
            const expected = EVariant.CATALOG;
            component.setVariant(expected);
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('completeDowload: singnal the completion of the download process to start the rendering', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.renderNodeList).toBeDefined();
            expect(componentAsAny.renderNodeList.length).toBe(0);
            component.completeDowload([new Node()]);
            expect(componentAsAny.renderNodeList.length).toBe(1);
        });
    });
    describe('Validating interfaces [IViewer]', () => {
        it('addSelection.success: add a node to the selection', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.selection.getFirstSelected()).toBeUndefined()
            component.addSelection(new Node())
            expect(componentAsAny.selection.getFirstSelected()).toBeDefined()
        });
        it('subtractSelection.true: add a node to the selection', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.selection.getFirstSelected()).toBeUndefined()
            const node = new Node()
            component.addSelection(node)
            expect(componentAsAny.selection.getFirstSelected()).toBeDefined()
            component.subtractSelection(node)
            expect(componentAsAny.selection.getFirstSelected()).toBeNull()
        });
        it('subtractSelection.false: add a node to the selection', () => {
            const componentAsAny = component as any;
            expect(componentAsAny.selection.getFirstSelected()).toBeUndefined()
            const node = new Node()
            component.addSelection(node)
            expect(componentAsAny.selection.getFirstSelected()).toBeDefined()
            component.subtractSelection(new Node())
            expect(componentAsAny.selection.getFirstSelected()).toBeDefined()
        });
        it('notifyDataChanged.success: check that nodes get processed when the root load completes', () => {
            const expected = new Node();
            let componentAny = component as any;
            // componentAny.dataModelRoot.push(expected);
            component.completeDowload([expected]);
            let obtained = component.getNodes2Render();
            expect(obtained).toBeDefined();
            expect(obtained.length).toBe(1);
        });
    });
});
