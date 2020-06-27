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
import { RequestForm } from '@domain/RequestForm.domain';
import { Part } from '@domain/Part.domain';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { V1NewModelPanelComponent } from './v1-new-model-panel.component';
import { Model } from '@domain/inventory/Model.domain';
import { ModelForm } from '@domain/inventory/ModelForm.domain';

describe('COMPONENT V1NewModelPanelComponent [Module: PRODUCTION]', () => {
    let component: V1NewModelPanelComponent;
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
                V1NewModelPanelComponent
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1NewModelPanelComponent);
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
            expect(component.visible).toBeUndefined()
            expect(component.partContainer).toBeUndefined()
            expect(component.model).toBeDefined()
            expect(component.model instanceof ModelForm).toBeTrue()
            expect(component.editing).toBeFalse()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('startEditing: signal the start of editing of the selected Model', async () => {
            expect(component.model.label).toBeUndefined()
            expect(component.visible).toBeUndefined()
            expect(component.editing).toBeFalse()
            const componentAsAny = component as any
            componentAsAny.partContainer = {
                startEditing: () => { }
            }
            spyOn(componentAsAny.partContainer, 'startEditing')
            jasmine.clock().install();
            component.startEditing(new Model({
                label: '-TEXT-MODEL-LABEL-'
            }))
            jasmine.clock().tick(300);
            expect(component.model instanceof ModelForm).toBeTrue()
            expect(component.model.label).toBe('-TEXT-MODEL-LABEL-')
            expect(component.visible).toBeTrue()
            expect(component.editing).toBeTrue()
            expect(componentAsAny.partContainer.startEditing).toHaveBeenCalled()
            jasmine.clock().uninstall()
        });
        it('stopEditing: signal the stop editing to close the panel', () => {
            component.editing=true
            component.visible=true
            component.stopEditing()
            expect(component.visible).toBeFalse()
            expect(component.editing).toBeFalse()
        });
        it('isFormValid.noParts: get the parts associated to the Request', () => {
            expect(component.isFormValid(true)).toBeFalse()
            expect(component.isFormValid(false)).toBeFalse()
        });
        it('isFormValid.parts: join the form with the part counter', () => {
            const componentAsAny = component as any
            componentAsAny.partContainer = {
                getDroppedParts: () => { return ["one item"] }
            }
            expect(component.isFormValid(true)).toBeTrue()
            expect(component.isFormValid(false)).toBeFalse()
        });
        it('saveModel.newModel: save the new Model to the backend', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0)
            component.editing=false
            componentAsAny.model = new ModelForm({
                label: '-LABEL-',
                price: 5,
                stockLevel: 8
            })
            componentAsAny.partContainer = {
                getPartIdList: () => { return ["one id","two id"] }
            }
            // spyOn(componentAsAny.router,'navigate')
            jasmine.clock().install();
            await component.saveModel()
            jasmine.clock().tick(800);
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(location.path()).toBe('/');
            jasmine.clock().uninstall()
        });
        it('saveModel.editModel: save the new Model to the backend', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0)
            component.editing=true
            componentAsAny.model = new ModelForm({
                label: '-LABEL-',
                price: 5,
                stockLevel: 8
            })
            componentAsAny.partContainer = {
                getPartIdList: () => { return ["one id","two id"] }
            }
            jasmine.clock().install();
            await component.saveModel()
            jasmine.clock().tick(800);
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(component.visible).toBeFalse()
            expect(component.editing).toBeFalse()
            jasmine.clock().uninstall()
        });
    });
});
