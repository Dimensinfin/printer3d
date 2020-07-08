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
import { Coil } from '@domain/Coil.domain';
import { V1ModelRenderComponent } from './v1-model-render.component';
import { Model } from '@domain/inventory/Model.domain';
import { PartStack } from '@domain/PartStack.domain';

describe('COMPONENT V1ModelRenderComponent [Module: RENDER]', () => {
    let component: V1ModelRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1ModelRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1ModelRenderComponent);
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
        it('getters: validate getter contract for Part', () => {
            const model = new Model({
                id: "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                label: '-TEST-LABEL-',
                price: 4,
                stockLeve: 4,
                active: true,
                imagePath: 'http://igg.go',
                partList: [
                    new PartStack({
                        stackCount: 3
                    })
                ]
            })
            const coilAsAny = model as any
            component.node = model
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getContentCount()).toBe(3)
            expect(component.getPrice()).toBe('4 €')
            expect(component.getComposingParts()).toBeDefined()
            expect(component.getComposingParts().length).toBe(1)
        });
    });
    describe('Coverage Phase [Interactions]', () => {
        it('mouseEnter: control the mouse enter/exit', () => {
            expect(component.inside).toBeFalse()
            component.mouseEnter({})
            expect(component.inside).toBeTrue()
            component.mouseLeave({})
            expect(component.inside).toBeFalse()
        });
        it('toggleEdition: change the model edit state', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                enterSelected: () => { }
            }
            spyOn(componentAsAny.container, 'enterSelected')
            expect(componentAsAny.editing).toBeFalse()
            component.toggleEdition()
            expect(componentAsAny.editing).toBeTrue()
            expect(componentAsAny.container.enterSelected).toHaveBeenCalledTimes(1)
            component.toggleEdition()
            expect(component.inside).toBeFalse()
            expect(componentAsAny.container.enterSelected).toHaveBeenCalledTimes(2)
        });
    });
});
