// - CORE
import { NO_ERRORS_SCHEMA, ChangeDetectorRef } from '@angular/core';
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
import { ToastrService } from 'ngx-toastr';
import { SupportToastrService } from '@app/testing/SupportToastrService.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { V1DockComponent } from '../../common/v1-dock/v1-dock.component';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { Part } from '@domain/Part.domain';
import { V1PartContainerRenderComponent } from './v1-part-container-render.component';

describe('COMPONENT V1PartContainerRenderComponent [Module: RENDER]', () => {
    let component: V1PartContainerRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1PartContainerRenderComponent
            ],
            providers: [
                { provide: ToastrService, useClass: SupportToastrService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: DialogFactoryService, useValue: {} },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
           ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1PartContainerRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getLabel:success check the "label" field when defined', () => {
            component.node = new Part({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 90,
                "cost": 1.0,
                "price": 6.0,
                "stockLevel": 5,
                "stockAvailable": 0,
                "imagePath": null,
                "modelPath": null,
                "active": true
            });
            expect(component.getLabel()).toBe("Boquilla Ganesha");
        });
        it('getLabel:failure check the "label" field when not initialized', () => {
            component.node = new Part();
            expect(component.getLabel()).toBeUndefined()
        });
        it('getDescription:success check the "description" field when defined', () => {
            component.node = new Part({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Descripcion de la Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 90,
                "cost": 1.0,
                "price": 6.0,
                "stockLevel": 5,
                "stockAvailable": 0,
                "imagePath": null,
                "modelPath": null,
                "active": true
            });
            expect(component.getDescription()).toBe("Descripcion de la Boquilla Ganesha");
        });
        it('getDescription:failure check the "description" field when not initialized', () => {
            component.node = new Part();
            expect(component.getDescription()).toBeUndefined()
        });
        it('getBuildTime:success check the "buildTime" field when defined', () => {
            component.node = new Part({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Descripcion de la Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 90,
                "cost": 1.0,
                "price": 6.0,
                "stockLevel": 5,
                "stockAvailable": 0,
                "imagePath": null,
                "modelPath": null,
                "active": true
            });
            expect(component.getBuildTime()).toBe('90 min.');
        });
        it('getDescription:failure check the "buildTime" field when not initialized', () => {
            component.node = new Part();
            expect(component.getBuildTime()).toBe('undefined min.')
        });
    });
});
