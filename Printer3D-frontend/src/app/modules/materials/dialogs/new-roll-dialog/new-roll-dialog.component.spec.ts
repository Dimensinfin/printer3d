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
import { AppStoreService } from '@app/services/app-store.service';
import { IsolationService } from '@app/platform/isolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { MatDialogHarness } from '@angular/material/dialog/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { Part } from '@domain/Part.domain';
import { NewRollDialogComponent } from './new-roll-dialog.component';
import { Roll } from '@domain/Roll.domain';

describe('COMPONENT NewRollDialogComponent [Module: INVENTORY]', () => {
    let component: NewRollDialogComponent;
    let isolationService: SupportIsolationService;
    let backendService: SupportBackendService;

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
                NewRollDialogComponent
            ],
            providers: [
                // { provide: MatDialog, useValue: {} },
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(NewRollDialogComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
        backendService = TestBed.get(BackendService);
        fixture.detectChanges();
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.roll).toBeDefined('field "roll" should have initial value.');
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', async () => {
            expect(component.roll.id).toBeDefined('Empty Roll should have id.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        xit('saveRoll: persist the roll on the backend repository', async () => {
            const roll: Roll = new Roll({ id: '-ID-' });
            const componentAsAny = component as any;
            componentAsAny.roll = roll;
            // const backendService = TestBed.get(BackendService);
            spyOn(backendService, 'apiNewRoll_v1');
            component.saveRoll();
            expect(backendService.apiNewRoll_v1).toHaveBeenCalled();
        });
        // it('savePartAndRepeat: persist the part on the backend repository', async () => {
        //     const part: Part = new Part({id: '-ID-'});
        //     const componentAsAny = component as any;
        //     componentAsAny.part = part;
        //     component.savePartAndRepeat();
        // });
        // it('closeModal: start closing the dialog', async () => {
        //     const componentAsAny = component as any;
        //     spyOn(componentAsAny.dialogRef, 'close');
        //     expect(componentAsAny.dialogRef).toBeDefined();
        //     component.closeModal();
        //     expect(componentAsAny.dialogRef.close).toHaveBeenCalled();
        // });
    });
});
