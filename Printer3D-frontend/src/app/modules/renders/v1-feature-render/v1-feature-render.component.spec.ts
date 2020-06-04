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
import { V1FeatureRenderComponent } from './v1-feature-render.component';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { V1DockComponent } from '../../shared/panels/v1-dock/v1-dock.component';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { BackendService } from '@app/services/backend.service';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';

describe('COMPONENT V1FeatureRenderComponent [Module: SHARED]', () => {
    let component: V1FeatureRenderComponent;
    let dock: V1DockComponent;
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
                V1FeatureRenderComponent,
                V1DockComponent
            ],
            providers: [
                { provide: DialogFactoryService, useValue: dialogFactoryService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1FeatureRenderComponent);
        component = fixture.componentInstance;
        const fixtureDock = TestBed.createComponent(V1DockComponent);
        dock = fixtureDock.componentInstance;
        // isolationService = TestBed.get(IsolationService);
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('onClick.null: run the action when there is no feature', () => {
            component.node = undefined;
            spyOn(dialogFactoryService, 'processClick');
            component.onClick();
            expect(dialogFactoryService.processClick).not.toHaveBeenCalled();
        });
        it('onClick.disabled: run the action then the feature is diasabled', () => {
            component.node = new Feature({ enabled: false });
            spyOn(dialogFactoryService, 'processClick');
            component.onClick();
            expect(dialogFactoryService.processClick).not.toHaveBeenCalled();
        });
        it('onClick.PAGEROUTE: run the action for a PAGEROUTE', () => {
            component.dock = dock;
            component.node = new Feature({
                "jsonClass": "Feature",
                "label": "/Inventory",
                "enabled": true,
                "active": false,
                "interaction": "PAGEROUTE",
                "route": "/inventory"
            });
            spyOn(dock, 'activateFeature');
            component.onClick();
            expect(dock.activateFeature).toHaveBeenCalled();
        });
        it('onClick.DIALOG: run the action connected to the feature click', async () => {
            component.dock = dock;
            component.node = new Feature({
                "jsonClass": "Feature",
                "label": "/Nueva Pieza",
                "enabled": true,
                "active": false,
                "interaction": "DIALOG",
                "dialog": "NewPartDialog"
            });
            spyOn(dialogFactoryService, 'processClick').and.returnValue(dialogRef)
            await component.onClick();
            expect(dialogFactoryService.processClick).toHaveBeenCalled();
        });
    });
});
