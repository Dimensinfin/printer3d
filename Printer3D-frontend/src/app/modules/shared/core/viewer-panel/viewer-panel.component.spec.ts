// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { ViewerPanelComponent } from './viewer-panel.component';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { Node } from '@domain/Node.domain';

describe('PANEL ViewerPanelComponent [Module: CORE]', () => {
    let component: ViewerPanelComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                ViewerPanelComponent
            ],
            providers: [
            ]
        })
            .compileComponents();
        const fixture = TestBed.createComponent(ViewerPanelComponent);
        component = fixture.componentInstance;
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.nodes2render).toBeDefined();
            expect(component.nodes2render.length).toBe(0);
            expect(component.downloadtitle).toBeUndefined();
            expect(component.downloader).toBeUndefined();
            expect(component.variant).toBe(EVariant.DEFAULT)
            expect(component.index).toBe(1)
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getVariant.success: check the variant field', () => {
            const expected = EVariant.PART_LIST
            component.variant = expected;
            let obtained = component.getVariant();
            expect(obtained).toBe(expected);
        });
        it('isDownloading.success: check the downloading flag', () => {
            const componentAsAny = component as any;
            expect(component.isDownloading()).toBeTrue();
            componentAsAny.downloader = { isDownloading: () => { return false; } }
            expect(component.isDownloading()).toBeFalse();
        });
        it('getNodes2Render.success: check the nodes to be rendered', () => {
            let componentAny = component as any;
            componentAny.nodes2render.push(new Node());
            let obtained = component.getNodes2Render();
            expect(obtained).toBeDefined();
            expect(obtained.length).toBe(1);
        });
        it('getNextIndex.success: check the next index counter', () => {
            expect(component.index).toBe(1);
            expect(component.getNextIndex()).toBe(1)
            expect(component.index).toBe(2);
        });
    });
});
