// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../../platform/platform-constants';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
// - MATERIAL
import { MatDialogConfig } from '@angular/material/dialog';
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
import { NewPartDialogComponent } from './new-part-dialog.component';
import { SupportMatDialogRef } from '@app/testing/SupporMatDialogRef.component';

import { MatDialogHarness } from '@angular/material/dialog/testing';
// import {MatDialogRefHarness} from '@angular/material/dialog/testing';

fdescribe('COMPONENT NewPartDialogComponent [Module: INVENTORY]', () => {
    let component: NewPartDialogComponent;
    let isolationService: SupportIsolationService;
    
    // const dialogFactory = new MatDialog();
    const dialogReference = new SupportMatDialogRef<NewPartDialogComponent>();
    let loader: HarnessLoader;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                ReactiveFormsModule,
                FormsModule
            ],
            declarations: [
                NewPartDialogComponent
            ],
            providers: [
                { provide: MatDialog, useValue: {} },
                { provide: MatDialogRef, useValue: { dialogReference } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(NewPartDialogComponent);
        component = fixture.componentInstance;
        loader = TestbedHarnessEnvironment.loader(fixture);
        isolationService = TestBed.get(IsolationService);
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            // expect(component.self).toBeDefined('field "self" not defined.');
            expect(componentAsAny.part).toBeDefined('field "part" should have initial value.');
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit.notPreviousPart: validate initialization flow', async function () {
            isolationService.removeFromStorage(platformconstants.PARTIAL_PART_KEY);
            expect(component.part.id).toBeUndefined('Empty Part should have no id.');
            // const configuration = [new Feature({ label: '/Inventory', active: false, route: 'inventory' })];
            // isolationService.setToStorageObject(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY, configuration);
            await component.ngOnInit();
            expect(component.part.id).toBeDefined('Empty Part should have id.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    fdescribe('Code Coverage Phase [Methods]', () => {
        it('closeModal: start closing the dialog', async () => {
            const matDialog: MatDialogHarness = await loader.getHarness(MatDialogHarness);
            const modalDialogRef: MatDialogRef<NewPartDialogComponent> = matDialog.open(dialogComponent, dialogConfig);

            component = new NewPartDialogComponent(matDialog, null);
            spyOn(dialogReference, 'close');
            component.closeModal();
            expect(dialogReference.close).toHaveBeenCalled();
        });
    });
});
