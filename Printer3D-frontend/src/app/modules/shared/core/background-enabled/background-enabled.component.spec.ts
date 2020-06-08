// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subscription } from 'rxjs';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { BackgroundEnabledComponent } from '../background-enabled/background-enabled.component';

describe('PANEL BackgroundEnabledComponent [Module: CORE]', () => {
    let component: BackgroundEnabledComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                BackgroundEnabledComponent
            ],
            providers: [
            ]
        })
            .compileComponents();
        const fixture = TestBed.createComponent(BackgroundEnabledComponent);
        component = fixture.componentInstance;
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0, 'The initial subscription list should be 0.');
            componentAsAny.backendConnections.push(new Subscription())
            component.ngOnDestroy()
            expect(componentAsAny.backendConnections.length).toBe(1, 'After initialization should be 1.');
        });
    });
});
