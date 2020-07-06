// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../platform/platform-constants';
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
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { V1DockComponent } from '../../common/v1-dock/v1-dock.component';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { V1CoilRenderComponent } from './v1-coil-render.component';
import { Coil } from '@domain/Coil.domain';

describe('COMPONENT V1CoilRenderComponent [Module: RENDER]', () => {
    let component: V1CoilRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CoilRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1CoilRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('getMaterial: get the material field', () => {
            component.node = new Coil({ material: "PLA" });
            expect(component.getMaterial()).toBe('PLA')
        });
        it('getColor: get the material field', () => {
            component.node = new Coil({ color: "RED" });
            expect(component.getColor()).toBe('RED')
        });
        it('getWeight: get the material field', () => {
            component.node = new Coil({ weight: 123 });
            expect(component.getWeight()).toBe('123 gr.')
        });
    });
});
