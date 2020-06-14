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
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { V1DockComponent } from './v1-dock.component';
import { Feature } from '@domain/Feature.domain';
import { AppComponent } from '@app/app.component';

describe('COMPONENT V1DockComponent [Module: SHARED]', () => {
    let component: V1DockComponent;
    let routerDetector: any = { refresh: () => { } };
    let location: Location;
    let router: Router;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                AppComponent,
                V1DockComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1DockComponent);
        component = fixture.componentInstance;
        location = TestBed.get(Location)
        router = TestBed.get(Router)
        router.initialNavigation()
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.self).toBeDefined('field "self" not defined.');
            expect(componentAsAny.activeFeature).toBeUndefined('field "activeFeature" has initial value and should not.');
            expect(componentAsAny.configuredFeatures).toBeDefined('field "configuredFeatures" not defined.');
            expect(componentAsAny.configuredFeatures.length).toBe(0);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', async () => {
            await component.ngOnInit();
            const componentAsAny = component as any;
            expect(componentAsAny.configuredFeatures.length).toBe(2);
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

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('activateFeature.null: clear the list of active features', () => {
            const componentAsAny = component as any;
            componentAsAny.routerDetector = routerDetector;
            expect(componentAsAny.activeFeature).toBeUndefined();
            componentAsAny.activeFeature = new Feature()
            expect(componentAsAny.activeFeature).toBeDefined()
            component.activateFeature(null);
            expect(componentAsAny.activeFeature).toBeUndefined();
            expect(location.path()).toBe('/');
        });
        it('activateFeature.firstTime: activate a new feature when there is none active', () => {
            const featureA = new Feature({ label: '/Inventario', active: false, route: 'inventory' });
            const featureB = new Feature({ label: '/Nueva Pieza', active: false });
            const componentAsAny = component as any;
            componentAsAny.routerDetector = routerDetector;
            componentAsAny.configuredFeatures.push(featureA);
            componentAsAny.configuredFeatures.push(featureB);
            expect(componentAsAny.activeFeature).toBeUndefined();
            component.activateFeature(featureA);
            expect(componentAsAny.activeFeature).toBe(featureA);
        });
        it('activateFeature.active: activate a new feature when there is one active', () => {
            const featureA = new Feature({ label: '/Inventario', active: false, route: 'inventory' });
            const featureB = new Feature({ label: '/Nueva Pieza', active: false });
            const componentAsAny = component as any;
            componentAsAny.routerDetector = routerDetector;
            componentAsAny.configuredFeatures.push(featureA);
            componentAsAny.configuredFeatures.push(featureB);
            expect(componentAsAny.activeFeature).toBeUndefined();
            component.activateFeature(featureA);
            expect(componentAsAny.activeFeature).toBe(featureA);
            component.activateFeature(featureB);
            expect(componentAsAny.activeFeature).toBe(featureB);
        });
        it('clean: clean the Dock configuration', () => {
            const componentAsAny = component as any;
            componentAsAny.configuredFeatures = []
            componentAsAny.configuredFeatures.push(new Feature());
            componentAsAny.configuredFeatures.push(new Feature());
            componentAsAny.configuredFeatures.push(new Feature());
            componentAsAny.configuredFeatures.push(new Feature());
            expect(componentAsAny.configuredFeatures.length).toBe(4);
            component.clean();
            expect(componentAsAny.configuredFeatures.length).toBe(2);
        });
    });
});
