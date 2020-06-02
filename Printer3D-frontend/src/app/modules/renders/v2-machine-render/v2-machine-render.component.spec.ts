// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { V2MachineRenderComponent } from './v2-machine-render.component';
import { Machine } from '@domain/Machine.domain';
import { Part } from '@domain/Part.domain';

describe('COMPONENT V2MachineRenderComponent [Module: SHARED]', () => {
    let component: V2MachineRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V2MachineRenderComponent,
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V2MachineRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.sessionTimer).toBeUndefined();
            expect(componentAsAny.node).toBeUndefined();
            expect(component.self).toBeUndefined();
            expect(component.target).toBeUndefined();
            expect(component.building).toBeFalse();
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.empty: validate initialization flow', async () => {
            await component.ngOnInit();
            const componentAsAny = component as any;
            expect(component.self).toBeDefined();
        });
        it('ngOnInit.building: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({ currentPart: new Part() });
            await component.ngOnInit();
            expect(component.self).toBeDefined();
            expect(componentAsAny.node).toBeDefined();
            expect(componentAsAny.node.currentPart).toBeDefined();
            expect(component.target).toBeDefined();
            expect(component.building).toBeTrue();
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('getRemainingTime: if a part is building get the remaining time', () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({ jobInstallmentDate: 'today' });
            expect(component.getRemainingTime()).toBe(23 * 60);
        });
        it('getRemainingTime: if a part is building get the remaining time', () => {
            const componentAsAny = component as any;
            component.onDrop({ dragData: new Part({ label: '-TEST-DATA-' }) })
            expect(componentAsAny.target.label).toBe('-TEST-DATA-');
        });
        it('startBuild: start the countdown built timer', () => {
            const componentAsAny = component as any;
            componentAsAny.target = new Part({ buildTime: 60 });
            componentAsAny.sessionTimer = {activate: (time: number) => { }}
            component.startBuild();
            expect(component.building).toBeTrue();
        });
        it('onClearClick: start the countdown built timer', () => {
            const componentAsAny = component as any;
            componentAsAny.target = new Part({ buildTime: 60 });
            componentAsAny.sessionTimer = {activate: (time: number) => { }}
            component.startBuild();
            expect(component.building).toBeTrue();
            component.onClearClick();
            expect(component.target).toBeNull();
            expect(component.building).toBeFalse();
        });
    });
});
