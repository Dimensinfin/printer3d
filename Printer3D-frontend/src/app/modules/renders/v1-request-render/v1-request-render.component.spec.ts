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
import { DockService } from '@app/services/dock.service';
import { V1RequestRenderComponent } from './v1-request-render.component';

xdescribe('COMPONENT V1RequestRenderComponent [Module: RENDERS]', () => {
    let component: V1RequestRenderComponent;
    let dockService: DockService
    let dialogRef = {
        afterClosed: () => {
            return Observable.create((observer) => {
                observer.next({})
                observer.complete();
            });
        }
    }
    let dialogFactoryService = { processClick: (feature: Feature) => { console.log('POINT'); return dialogRef } };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1RequestRenderComponent,
                V1DockComponent
            ],
            providers: [
                { provide: DialogFactoryService, useValue: dialogFactoryService },
                { provide: DockService, useClass: DockService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1RequestRenderComponent);
        component = fixture.componentInstance;
        dockService = TestBed.get(DockService)
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
            expect(component.node).toBeUndefined()
        });
    });
});
