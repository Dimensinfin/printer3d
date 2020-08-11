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
import { V1PartStackRenderComponent } from './v1-part-stack-render.component';
import { PartStack } from '@domain/PartStack.domain';

describe('COMPONENT V1PartStackRenderComponent [Module: RENDER]', () => {
    let component: V1PartStackRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1PartStackRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1PartStackRenderComponent);
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
        it('getters: check the getter results', () => {
            component.node = new PartStack({
                id: '-PART-STACK-UNIQUE-ID-',
                label: '-PART-STACK-LABEL-',
                stackCount: 7,
                material: 'FLEX',
                color: 'TURQUESA'
            })
            expect(component.getNode()).toBeDefined()
            expect(component.getNode() instanceof PartStack).toBeTrue()
            expect(component.getUniqueId()).toBe('-PART-STACK-UNIQUE-ID-')
            expect(component.getRequired()).toBe(7)
            expect(component.getLabel()).toBe('-PART-STACK-LABEL-')
            expect(component.getMaterial()).toBe('FLEX')
            expect(component.getColor()).toBe('TURQUESA')
        });
        it('removePart: pass the event to remove a part', () => {
            component.node = new PartStack({
                id: '-PART-STACK-UNIQUE-ID-',
                label: '-PART-STACK-LABEL-',
                stackCount: 7,
                material: 'FLEX',
                color: 'TURQUESA'
            })
            const componentAsAny = component as any
            componentAsAny.container = { removePart: (data: any) => { } }
            spyOn(componentAsAny.container, 'removePart')
            component.removePart()
            expect(componentAsAny.container.removePart).toHaveBeenCalled()
        });
    });
});
