// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../../platform/platform-constants';
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
import { Location } from "@angular/common";
// - PROVIDERS
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { V1NewRequestPanelComponent } from './v1-new-request-panel.component';
import { RequestForm } from '@domain/dto/RequestForm.dto';
import { Part } from '@domain/Part.domain';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('COMPONENT V1NewRequestPanelComponent [Module: PRODUCTION]', () => {
    let component: V1NewRequestPanelComponent;
    let isolationService: SupportIsolationService;
    let location: Location;
    let router: Router;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                ReactiveFormsModule,
                FormsModule,
            ],
            declarations: [
                V1NewRequestPanelComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1NewRequestPanelComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(SupportIsolationService)
        location = TestBed.get(Location)
        router = TestBed.get(Router)
        router.initialNavigation()
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
            expect(component.self).toBeDefined();
            expect(component.request).toBeDefined();
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('getRequestDate: get the request date to be used when saving the Request', () => {
            expect(component.getRequestDate()).toBeDefined();
        });
        it('getLabel: get the label entered by the user', () => {
            const expected: string = isolationService.generateRandomString(32);
            component.request = new RequestForm({ label: expected })
            expect(component.getLabel()).toBe(expected);
        });
        it('getRequestParts: get the parts associated to the Request', () => {
            expect(component.getRequestParts()).toBeDefined()
            expect(component.getRequestParts().length).toBe(0)
            component.onDrop({ dragData: new Part() })
            expect(component.getRequestParts().length).toBe(1)
        });
        it('getRequestParts: get the parts associated to the Request', () => {
            expect(component.hasParts()).toBeFalse()
            component.onDrop({ dragData: new Part() })
            expect(component.hasParts()).toBeTrue()
        });
        it('isFormValid.noParts: get the parts associated to the Request', () => {
            expect(component.isFormValid(true)).toBeFalse()
            expect(component.isFormValid(false)).toBeFalse()
        });
        it('isFormValid.parts: get the parts associated to the Request', () => {
            component.onDrop({ dragData: new Part() })
            expect(component.isFormValid(true)).toBeTrue()
            expect(component.isFormValid(false)).toBeFalse()
        });
        it('onDrop: get the parts associated to the Request', () => {
            expect(component.getRequestParts().length).toBe(0)
            component.onDrop({ dragData: new Part() })
            expect(component.getRequestParts().length).toBe(1)
        });
        it('removePart: get the parts associated to the Request', () => {
            expect(component.getRequestParts().length).toBe(0)
            const part: Part = new Part()
            component.onDrop({ dragData: part })
            component.onDrop({ dragData: part })
            expect(component.getRequestParts().length).toBe(2)
            component.removePart(part)
            expect(component.getRequestParts().length).toBe(0)
        });
        it('saveRequest: save the Request to the backend', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0)
            await component.saveRequest()
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(location.path()).toBe('/');
        });
        it('cancelRequest: cancel the request', async () => {
            await component.cancelRequest()
            expect(location.path()).toBe('/');
        });
    });
});
