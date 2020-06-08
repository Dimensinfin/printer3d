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
import { NewPartDialogComponent } from './new-part-dialog.component';

import { MatDialogHarness } from '@angular/material/dialog/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { Part } from '@domain/Part.domain';

describe('COMPONENT NewPartDialogComponent [Module: INVENTORY]', () => {
    let component: NewPartDialogComponent;
    let isolationService: SupportIsolationService;

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
                // { provide: MatDialog, useValue: {} },
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(NewPartDialogComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
        fixture.detectChanges();
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.part).toBeDefined('field "part" should have initial value.');
        });
    });

    // - O N I N I T I A T I Z A T I O N   P H A S E
    /**
     * OnInit runs when the component is created. So use a clean component for this tests.
     * Because the OnInit is run two time I should clean the object to the initial stage before running the second.
     */
    describe('On Initialization Phase', async () => {
        it('ngOnInit.notPreviousPart: validate initialization flow', async () => {
            component.part = new Part();
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            isolationService.removeFromStorage(platformconstants.PARTIAL_PART_KEY);
            component.ngOnInit(); // Remember that components run the ngOnInit when nstantiated by the testbed.
            console.log('[ngOnInit.notPreviousPart: validate initialization flow]> part: ' + JSON.stringify(component.part));
            expect(component.part.id).toBeDefined('Empty Part should have id.');
            expect(component.part.material).toBe('PLA', 'The expected default material does not match.');
            expect(component.part.colorCode).toBe('INDEFINIDO', 'The expected default color does not match.');
        });
        it('ngOnInit.previousPart: validate initialization flow', async () => {
            component.part = new Part();
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            const part: Part = new Part({ id: '-ID-' });
            isolationService.setToStorageObject(platformconstants.PARTIAL_PART_KEY, part);
            component.ngOnInit(); // Remember that components run the ngOnInit when nstantiated by the testbed.
            expect(component.part.id).toBeDefined('Empty Part should have id.');
        });
        it('ngOnInit.validateFinisings: validate initialization flow with finishings', async () => {
            component.part = new Part();
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            component.ngOnInit(); // Remember that components run the ngOnInit when nstantiated by the testbed.
            const data = component.finishings;
            console.log('[ngOnInit.validateFinisings]> finishings: ' + JSON.stringify(data))
            expect(data).toBeDefined('Check that finishing is not empty');
            expect(data.size).toBe(3, 'The number of materials does not match');
            console.log('[ngOnInit.validateFinisings]> materials: ' + component.materials)
            expect(component.materials.length).toBe(3, 'The number of materials does not match');
            const obtained = component.finishings.get('PLA')
            console.log('[ngOnInit.validateFinisings]> obtained: ' + obtained)
            expect(obtained.length).toBe(4, 'The number of colors does not match');
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(1, 'The initial subscription list should be 1.');
            component.ngOnInit(); // Remember that components run the ngOnInit when nstantiated by the testbed.
            expect(componentAsAny.backendConnections.length).toBe(2, 'After initialization should be 2.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('savePart: persist the part on the backend repository', async () => {
            const part: Part = new Part({ id: '-ID-' });
            const componentAsAny = component as any;
            componentAsAny.part = part;
            component.savePart();
        });
        it('savePartAndRepeat: persist the part on the backend repository', async () => {
            const part: Part = new Part({ id: '-ID-' });
            const componentAsAny = component as any;
            componentAsAny.part = part;
            console.log('[savePartAndRepeat: persist the part on the backend repository]> about to call "savePartAndRepeat"')
            await component.savePartAndRepeat();
            // expect(component.part.colorCode).toBe('INDEFINIDO')
        });
        it('closeModal: start closing the dialog', async () => {
            const componentAsAny = component as any;
            spyOn(componentAsAny.dialogRef, 'close');
            expect(componentAsAny.dialogRef).toBeDefined();
            component.closeModal();
            expect(componentAsAny.dialogRef.close).toHaveBeenCalled();
        });
    });
});
