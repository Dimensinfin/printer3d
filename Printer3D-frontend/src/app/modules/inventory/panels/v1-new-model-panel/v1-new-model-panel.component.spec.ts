// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../../platform/platform-constants';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
// - MATERIAL
import { MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
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
import { HarnessLoader } from '@angular/cdk/testing';
import { TestbedHarnessEnvironment } from '@angular/cdk/testing/testbed';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/app-store.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
// - DOMAIN
import { V1NewModelPanelComponent } from './v1-new-model-panel.component';
import { NewPartDialogComponent } from '../../dialogs/new-part-dialog/new-part-dialog.component';

xdescribe('V1NewModelPanelComponent', () => {
    let component: V1NewModelPanelComponent;
    let fixture: ComponentFixture<V1NewModelPanelComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                ReactiveFormsModule,
                FormsModule,
                MatDialogModule
            ],
            declarations: [
                NewPartDialogComponent
            ],
            providers: [
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1NewModelPanelComponent);
        component = fixture.componentInstance;
        // isolationService = TestBed.get(IsolationService);
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(V1NewModelPanelComponent);
        component = fixture.componentInstance;
        // fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
