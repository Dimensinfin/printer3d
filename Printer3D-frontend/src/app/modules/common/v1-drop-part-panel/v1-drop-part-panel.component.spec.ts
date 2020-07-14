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
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { EVariant, RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { Request } from '@domain/Request.domain';
import { Part } from '@domain/Part.domain';
import { V1DropPartPanelComponent } from './v1-drop-part-panel.component';
import { PartStack } from '@domain/PartStack.domain';

describe('COMPONENT V1DropPartPanelComponent [Module: PRODUCTION]', () => {
    let component: V1DropPartPanelComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1DropPartPanelComponent,
            ],
            providers: [
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1DropPartPanelComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            const componentAsAny = component as any
            expect(component.self).toBeUndefined()
            expect(component.droppedParts).toBeDefined()
            expect(component.droppedParts.length).toBe(0)
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.empty: validate initialization flow', () => {
            const componentAsAny = component as any;
            component.ngOnInit();
            expect(component.self).toBeDefined();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('startEditing: get the request date to be used when saving the Request', () => {
            expect(component.droppedParts.length).toBe(0)
            component.startEditing([new PartStack()])
            expect(component.droppedParts.length).toBe(1)
        });
        it('addPart: get the request date to be used when saving the Request', () => {
            expect(component.droppedParts.length).toBe(0)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(1)
            component.addPart(new Part({ id: "0834cd30-e8cc-40fe-92b3-fe9ed3d10668" }))
            expect(component.droppedParts.length).toBe(1)
            component.addPart(new Part({ id: "3f7d2477-59ff-4444-bfb8-8c836be4ae5c" }))
            expect(component.droppedParts.length).toBe(2)
        });
    });
});
