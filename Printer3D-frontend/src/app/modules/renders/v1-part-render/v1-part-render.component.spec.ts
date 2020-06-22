// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../platform/platform-constants';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
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
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { V1DockComponent } from '../../shared/panels/v1-dock/v1-dock.component';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { Part } from '@domain/Part.domain';
import { V1PartRenderComponent } from './v1-part-render.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';

describe('COMPONENT V1PartRenderComponent [Module: RENDER]', () => {
    let component: V1PartRenderComponent;
    let testPart: Part = new Part({
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

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1PartRenderComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1PartRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.editPart).toBeDefined();
            expect(component.editing).toBeFalse()
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(componentAsAny.dataToPartTransformer).toBeDefined();
        });
        it('constructor.created: validate initial created instances', () => {
            const componentAsAny = component as any;
            const transformer: ResponseTransformer = componentAsAny.dataToPartTransformer;
            expect(transformer.transform({}) instanceof Part).toBeTrue();
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: check the unsubscription', async () => {
            const componetAsAny = component as any;
            expect(componetAsAny.backendConnections.length).toBe(0);
            component.node = testPart
            component.editPart = testPart
            await component.saveEditing();
            expect(componetAsAny.backendConnections.length).toBe(1);
            await component.ngOnDestroy();
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getLabel:success check the "label" field when defined', () => {
            component.node = testPart;
            expect(component.getLabel()).toBe("Boquilla Ganesha");
        });
        it('getMaterial:success check the "material" field when defined', () => {
            component.node = testPart;
            expect(component.getMaterial()).toBe("PLA");
        });
        it('getColor:success check the "color" field when defined', () => {
            component.node = testPart
            expect(component.getColor()).toBe("GRIS");
        });
        it('getCost:success check the "cost" field when defined', () => {
            component.node = testPart
            expect(component.getCost()).toBe('1 €');
        });
        it('getPrice:success check the "price" field when defined', () => {
            component.node = testPart
            expect(component.getPrice()).toBe('6 €');
        });
        it('getStockRequired:success check the "stockLevel" field when defined', () => {
            component.node = testPart
            expect(component.getStockRequired()).toBe(5);
        });
        it('getStockAvailable:success check the "stockAvailable" field when defined', () => {
            component.node = testPart
            expect(component.getStockAvailable()).toBe(0);
        });
        it('getActive:active check the "active" field when defined', () => {
            testPart.active = true;
            component.node = testPart
            expect(component.getActive()).toBe('ACTIVA');
        });
        it('getActive:inactive check the "active" field when defined', () => {
            testPart.active = false;
            component.node = testPart
            expect(component.getActive()).toBe('FUERA PROD.');
        });
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('toggleEdition: check the change on the editing', () => {
            component.node = testPart;
            component.toggleEdition()
            expect(component.editing).toBeTrue();
            component.toggleEdition()
            expect(component.editing).toBeFalse();
            component.toggleEdition()
            expect(component.editing).toBeTrue();
        });
        it('saveEditing: check the save flow', async () => {
            testPart.active = true;
            component.node = testPart
            component.editPart = testPart
            expect(component.getMaterial()).toBe("PLA");
            expect(component.getColor()).toBe("GRIS");
            expect(component.getCost()).toBe('1 €');
            expect(component.getPrice()).toBe('6 €');
            expect(component.getStockRequired()).toBe(5);
            expect(component.getStockAvailable()).toBe(0);
            expect(component.getActive()).toBe('ACTIVA');
            component.editing = false;
            expect(component.editing).toBeFalse();
            jasmine.clock().install();
            await component.saveEditing();
            jasmine.clock().tick(1100);
            jasmine.clock().uninstall()
            expect(component.editing).toBeTrue();
            expect(component.getMaterial()).toBe("PLA");
            expect(component.getColor()).toBe("WHITE");
            expect(component.getCost()).toBe('0.85 €');
            expect(component.getPrice()).toBe('4 €');
            expect(component.getStockRequired()).toBe(3);
            expect(component.getStockAvailable()).toBe(4);
            expect(component.getActive()).toBe('ACTIVA');
            jasmine.clock().uninstall()
        });
    });
});
