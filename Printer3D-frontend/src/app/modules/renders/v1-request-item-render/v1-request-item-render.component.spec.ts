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
import { IsolationService } from '@app/platform/isolation.service';
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
import { Coil } from '@domain/inventory/Coil.domain';
import { V1RequestItemRenderComponent } from './v1-request-item-render.component';
import { RequestItem } from '@domain/RequestItem.domain';
import { Part } from '@domain/Part.domain';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { Model } from '@domain/inventory/Model.domain';

describe('COMPONENT V1RequestItemRenderComponent [Module: RENDER]', () => {
    let component: V1RequestItemRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1RequestItemRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1RequestItemRenderComponent);
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
            const coil = new RequestItem({
                "itemId": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "type": "PART",
                "quantity": 4,
                "missing": 1,
                required: 3,
                content: new Part({
                    label: '-TEST-LABEL-',
                    price: 4,
                    material: 'PLA',
                    color: 'BLUE'
                })
            })
            const coilAsAny = coil as any
            component.node = coil
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.hasMissing()).toBeTrue()
            expect(component.getMissing()).toBe('1')
            coilAsAny.missing = 0
            expect(component.getMissing()).toBe('-')
            expect(component.getRequired()).toBe(4)
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getPrice()).toBe('16 €')
            expect(component.getMaterial()).toBe('PLA')
            expect(component.getColor()).toBe('BLUE')
        });
        it('getters: validate getter contract for Model', () => {
            const coil = new RequestItem({
                "itemId": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "type": "MODEL",
                "quantity": 4,
                "missing": 1,
                required: 3,
                content: new Model({
                    label: '-TEST-LABEL-',
                    price: 4
                })
            })
            const coilAsAny = coil as any
            component.node = coil
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.hasMissing()).toBeTrue()
            expect(component.getMissing()).toBe('1')
            coilAsAny.missing = 0
            expect(component.getMissing()).toBe('-')
            expect(component.getRequired()).toBe(4)
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getPrice()).toBe('16 €')
            expect(component.getMaterial()).toBe('-')
            expect(component.getColor()).toBe('-')
        });
    });
    describe('Coverage Phase [Methods]', () => {
        it('removeContent: send the event to remove a content', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                removeContent: () => { }
            }
            spyOn(componentAsAny.container, 'removeContent')
            component.removeContent()
            expect(componentAsAny.container.removeContent).toHaveBeenCalled()
        });
    });
});
