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
import { V2MachinesPanelComponent } from './v2-machines-panel.component';

fdescribe('COMPONENT V2MachinesPanelComponent [Module: SHARED]', () => {
    let component: V2MachinesPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V2MachinesPanelComponent,
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V2MachinesPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(component.active).toBeTrue();
            expect(component.machines).toBeDefined();
            expect(component.machines.length).toBe(0);
        });
    });
});
