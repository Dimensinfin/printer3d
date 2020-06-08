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
import { TIMEOUT } from 'dns';

const TEST_TIME: number = 12 * 60;

describe('COMPONENT V2MachineRenderComponent [Module: SHARED]', () => {
    let component: V2MachineRenderComponent;
    let fixture: ComponentFixture<V2MachineRenderComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V2MachineRenderComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(V2MachineRenderComponent);
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
            expect(component.buildTime).toBe(0);
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
        it('ngOnInit.idle: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine();
            await component.ngOnInit();
            expect(component.self).toBeDefined();
        });
        it('ngOnInit.running: validate initialization flow', async () => {
            const componentAsAny = component as any;
            componentAsAny.node = new Machine({
                currentJobPartId: new Part(),
                buildTime: TEST_TIME,
                jobInstallmentDate: Date.now().toString,
                buildRecord: {
                    part: new Part(),
                    remainingTime: 10
                },
                isRunning: () => { return true }
            });
            await component.ngOnInit();
            expect(component.self).toBeDefined();
            expect(componentAsAny.node).toBeDefined();
            expect(componentAsAny.node.currentJobPartId).toBeDefined();
            expect(component.target).toBeDefined();
            expect(component.building).toBeTrue();
            console.log('>[ngOnInit.running: validate initialization flow]> BuildTime: ' + component.buildTime)
            expect(component.buildTime).toBe(600, 'Expect the build time to be the constant.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('isAutostart:false true if the part is loaded from a running machine', () => {
            expect(component.building).toBeFalse();
            expect(component.isAutostart()).toBeFalse();
        });
        it('isAutostart:true true if the part is loaded from a running machine', () => {
            expect(component.building).toBeFalse();
            const componentAsAny = component as any;
            componentAsAny.building = true
            expect(component.isAutostart()).toBeTrue();
        });
        it('getBuildTime: get the time left to build', () => {
            expect(component.getBuildTime()).toBe(0);
            component.buildTime = 100;
            expect(component.getBuildTime()).toBe(100);
        });
        it('onDrop.empty: drop an empty element', () => {
            component.onDrop(null);
            expect(component.target).toBeUndefined();
        });
        it('onDrop.part: drop an empty element', () => {
            component.onDrop({ dragData: { buildTime: 300 } });
            expect(component.target).toBeDefined();
            expect(component.buildTime).toBe(300 * 60);
        });
        it('startBuild: start the countdown built timer', async () => {
            const componentAsAny = component as any;
            componentAsAny.sessionTimer = {
                activate: () => { }
            }
            componentAsAny.node = {
                id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b",
                isRunning: () => { return false; }
            }
            componentAsAny.target = { id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" }
            expect(componentAsAny.backendConnections.length).toBe(0);
            await component.startBuild();
            expect(componentAsAny.backendConnections.length).toBe(1);
            expect(component.building).toBeTrue();
        });
        it('onClearClick: start the countdown built timer', async () => {
            const componentAsAny = component as any;
            componentAsAny.sessionTimer = {
                deactivate: () => { }
            }
            componentAsAny.node = {
                id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b",
                isRunning: () => { return false; }
            }
            componentAsAny.target = { id: "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" }
            expect(componentAsAny.backendConnections.length).toBe(0);
            // await component.startBuild();
            await component.onClearClick();
            expect(component.node).toBeDefined()
            expect(component.target).toBeNull()
            expect(component.building).toBeFalse()
            expect(componentAsAny.backendConnections.length).toBe(1);
        });
    });
});
