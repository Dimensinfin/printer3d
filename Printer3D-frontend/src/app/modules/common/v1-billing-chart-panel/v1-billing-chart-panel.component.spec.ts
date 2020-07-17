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
import { Feature } from '@domain/Feature.domain';
import { AppComponent } from '@app/app.component';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { V1BillingChartPanelComponent } from './v1-billing-chart-panel.component';

describe('COMPONENT V1BillingChartPanelComponent [Module: COMMON]', () => {
    let component: V1BillingChartPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1BillingChartPanelComponent
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1BillingChartPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.yscale).toBe(10);
            expect(component.billingChartData).toBeDefined()
            expect(component.billingChartData.length).toBe(0);
            expect(component.colorScheme.domain).toEqual(['blueviolet']);
            expect(component.yaxisTicks).toBeDefined()
            expect(component.yaxisTicks.length).toBe(1);
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', async () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            jasmine.clock().install();
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.ngOnInit();
            jasmine.clock().tick(4500);
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(component.billingChartData.length).toBe(4)
            expect(component.yaxisTicks.length).toBe(4)
            jasmine.clock().uninstall()
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('hasData: test if the char data is available', () => {
            expect(component.hasData()).toBeFalse()
            component.ngOnInit()
            expect(component.hasData()).toBeTrue()
        });
    });
});
