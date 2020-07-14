// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../../../../platform/platform-constants';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { v4 as uuidv4 } from 'uuid';
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
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { MatDialogHarness } from '@angular/material/dialog/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { Part } from '@domain/Part.domain';
import { NewCoilDialogComponent } from './new-coil-dialog.component';
import { Coil } from '@domain/Coil.domain';

describe('COMPONENT NewCoilDialogComponent [Module: INVENTORY]', () => {
    let component: NewCoilDialogComponent;
    let fixture: ComponentFixture<NewCoilDialogComponent>;

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
                NewCoilDialogComponent
            ],
            providers: [
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(NewCoilDialogComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(componentAsAny.coil).toBeDefined('field "coil" should have initial value.');
        });
    });

    // - O N I N I A T I Z A T I O N   P H A S E
    describe('On Initialization Phase', () => {
        it('ngOnInit: validate initialization flow', async () => {
            expect(component.coil.id).toBeUndefined();
            component.ngOnInit();
            expect(component.coil.id).toBeDefined('Empty Coil should have id.');
        });
    });

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: validate destruction flow', async () => {
            const componentAsAny = component as any;
            expect(componentAsAny.backendConnections.length).toBe(0, 'The initial subscription list should be 0.');
            component.saveCoil();
            expect(componentAsAny.backendConnections.length).toBe(1, 'After initialization should be 1.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('saveCoil: persist the Coil on the backend repository', async () => {
            const coil: Coil = new Coil({ id: '-ID-' });
            const componentAsAny = component as any;
            component.coil = coil;
            expect(componentAsAny.backendConnections.length).toBe(0, 'The number of subscriptions should be 0.')
            spyOn(component, 'closeModal').and.callThrough()
            await component.saveCoil();
            expect(componentAsAny.backendConnections.length).toBe(1, 'The number of subscriptions should be 1.')
            expect(component.closeModal).toHaveBeenCalled();
        });
        it('saveCoilAndRepeat: persist the Coil on the backend repository', async () => {
            const coil: Coil = new Coil({
                "id": "df23d097-4a7c-40e2-837f-e716459a526d",
                "material": "PLA",
                "color": "ROJO",
                "weight": 1000
            });
            component.coil = coil;
            console.log('[saveCoilAndRepeat: persist the Coil on the backend repository]> about to call "saveCoilAndRepeat"')
            expect(component.coil.id).toBe('df23d097-4a7c-40e2-837f-e716459a526d', 'Initial id should be the mock value');
            await component.saveCoilAndRepeat();
            console.log('>[saveCoilAndRepeat]> Coil: ' + JSON.stringify(component.coil))
            expect('df23d097-4a7c-40e2-837f-e716459a526d' === component.coil.id).toBeFalse();
            expect(component.coil.material).toBe('PLA');
            expect(component.coil.color).toBe('')
            expect(component.coil.weight).toBeUndefined('Weight should be undefined');
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
