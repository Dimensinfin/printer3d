// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from "@angular/common";
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { V1DockComponent } from './v1-dock.component';
import { Feature } from '@domain/Feature.domain';
import { AppComponent } from '@app/app.component';
import { DockService } from '@app/services/dock.service';
import { SupportDockService } from '@app/testing/SupportDock.service';

describe('COMPONENT V1DockComponent [Module: SHARED]', () => {
    let component: V1DockComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                // RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                // AppComponent,
                V1DockComponent
            ],
            providers: [
                { provide: DockService, useClass: SupportDockService },
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1DockComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.configuredFeatures).toBeDefined('field "configuredFeatures" not defined.');
            expect(componentAsAny.configuredFeatures.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', async () => {
            jasmine.clock().install();
            await component.ngOnInit();
            jasmine.clock().tick(500);
            const componentAsAny = component as any;
            expect(componentAsAny.configuredFeatures.length).toBe(2);
            jasmine.clock().uninstall()
        });
    });

    // - G E T T E R S   P H A S E
    describe('Getter Phase', () => {
        it('getActiveFeatures: get the list of Features docked', () => {
            let obtained = component.getActiveFeatures();
            expect(obtained).toBeDefined('initial dock configuration should be empty.');
            expect(obtained.length).toBe(0, 'initial dock configuration should be empty.');
            const componentAsAny = component as any;
            componentAsAny.configuredFeatures.push(new Feature({ label: '/Inventory', active: true }));
            obtained = component.getActiveFeatures();
            expect(obtained).toBeDefined('initial dock configuration should be empty.');
            expect(obtained.length).toBe(1, 'initial dock configuration should be empty.');
        });
        it('getActiveFeatures.null: get the list of Features docked', () => {
            const componentAsAny = component as any;
            componentAsAny.configuredFeatures = null;
            let obtained = component.getActiveFeatures();
            expect(obtained).toBeDefined('initial dock configuration should be empty.');
            expect(obtained.length).toBe(0, 'initial dock configuration should be empty.');
        });
    });
});
