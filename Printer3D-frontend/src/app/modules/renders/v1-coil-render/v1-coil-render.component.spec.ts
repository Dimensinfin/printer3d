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
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { FormsModule } from '@angular/forms';
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
                { provide: ToastrService, useClass: SupportToastrService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: DialogFactoryService, useValue: {} },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
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

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters: validate getter contract', () => {
            const coil = new Coil({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "material": "PLA",
                "color": "RED",
                "weight": 800
            })
            component.node = coil
            expect(component).toBeDefined();
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe('RED')
            expect(component.getWeight()).toBe('800 gr.')
        });
    });
    describe('Coverage Phase [Editing]', () => {
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse();
        });
        it('toggleEdition: check the change on the editing', () => {
            const coil = new Coil({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "material": "PLA",
                "color": "RED",
                "weight": 800
            })
            component.node = coil;
            component.toggleEdition()
            expect(component.editing).toBeTrue();
            component.toggleEdition()
            expect(component.editing).toBeFalse();
            component.toggleEdition()
            expect(component.editing).toBeTrue();
        });
        it('saveEditing: check the save flow', async () => {
            const coil = new Coil({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "material": "PLA",
                "color": "RED",
                "weight": 800
            })
            component.node = coil
            component.editCoil = coil
            expect(component.getMaterial()).toBe("PLA");
            expect(component.getColor()).toBe("RED");
            expect(component.getWeight()).toBe('800 gr.');
            component.editing = true;
            expect(component.editing).toBeTrue();
            jasmine.clock().install();
            await component.saveEditing();
            jasmine.clock().tick(1100);
            expect(component.editing).toBeFalse();
            expect(component.getMaterial()).toBe("PLA");
            expect(component.getColor()).toBe("ROJO");
            expect(component.getWeight()).toBe('750 gr.');
            jasmine.clock().uninstall()
        });
    });
});
