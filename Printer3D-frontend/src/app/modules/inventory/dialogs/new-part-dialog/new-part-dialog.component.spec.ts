// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { Printer3DConstants } from '../../../../platform/Printer3DConstants.platform';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
// - MATERIAL
import { MatLegacyDialogModule as MatDialogModule } from '@angular/material/legacy-dialog';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
// - TESTING
import { inject } from '@angular/core/testing';
import { waitForAsync } from '@angular/core/testing';
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
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
// - DOMAIN
import { NewPartDialogComponent } from './new-part-dialog.component';
import { Part } from '@domain/inventory/Part.domain';

describe('COMPONENT NewPartDialogComponent [Module: INVENTORY]', () => {
    let component: NewPartDialogComponent;
    let isolationService: SupportIsolationService;

    beforeEach(waitForAsync(() => {
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
                { provide: BackendService, useClass: SupportBackendService }
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(NewPartDialogComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
            expect(component.part).toBeDefined('field "part" should have initial value.');
            expect(component.finishings).toBeDefined();
            expect(component.materials).toBeDefined();
            expect(component.colors).toBeDefined();
            expect(componentAsAny.backendConnections).toBeDefined();
            expect(componentAsAny.backendConnections.length).toBe(0);
            expect(componentAsAny.dataToPartTransformer).toBeDefined()
        });
    });

    // - O N I N I T I A T I Z A T I O N   P H A S E
    /**
     * OnInit runs when the component is created. So use a clean component for this tests.
     * Because the OnInit is run two times I should clean the object to the initial stage before running the second.
     */
    describe('On Initialization Phase', async () => {
        it('ngOnInit.notPreviousPart: validate initialization flow', async () => {
            component.part = new Part();
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            isolationService.removeFromStorage(Printer3DConstants.PARTIAL_PART_KEY);
            jasmine.clock().install();
            component.ngOnInit();
            jasmine.clock().tick(200);
            console.log('[ngOnInit.notPreviousPart: validate initialization flow]> part: ' + JSON.stringify(component.part));
            expect(component.part.id).toBeDefined('Empty Part should have id.');
            expect(component.part.material).toBe('PLA', 'The expected default material does not match.');
            expect(component.part.color).toBeUndefined('The expected default color does not match.');
            jasmine.clock().uninstall()
        });
        it('ngOnInit.previousPart: validate initialization flow', async () => {
            const originalPart = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            });
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            isolationService.setToStorageObject(Printer3DConstants.PARTIAL_PART_KEY, originalPart);
            jasmine.clock().install();
            component.part = undefined
            component.ngOnInit();
            jasmine.clock().tick(200);
            expect(component.part.id).toBeDefined('Previous Part should have id.');
            expect(component.part.label).toBe("Covid-19 Key");
            jasmine.clock().uninstall()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('savePart: persist the part on the backend repository', async () => {
            const part: Part = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            })
            const componentAsAny = component as any;
            componentAsAny.dialogRef = {
                close: () => { }
            }
            spyOn(componentAsAny.dialogRef, 'close')
            component.part = part;
            jasmine.clock().install();
            component.savePart();
            jasmine.clock().tick(200);
            expect(componentAsAny.dialogRef.close).toHaveBeenCalled()
            jasmine.clock().uninstall()
        });
        it('savePartAndRepeat: persist the part on the backend repository', async () => {
            const part: Part = new Part({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "color": "WHITE",
                "buildTime": 15,
                "cost": 0.85,
                "price": 4,
                "stockLevel": 3,
                "stockAvailable": 4,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            })
            const componentAsAny = component as any;
            componentAsAny.dialogRef = {
                close: () => { }
            }
            spyOn(componentAsAny.dialogRef, 'close')
            component.part = part;
            jasmine.clock().install();
            component.savePartAndRepeat();
            jasmine.clock().tick(200);
            expect(component.part.id).toBeDefined('Previous Part should have id.');
            expect(component.part.label).toBe("Covid-19 Key");
            expect(component.part.material).toBe("PLA");
            expect(component.part.color).toBeUndefined()
            jasmine.clock().uninstall()
        });
        it('closeModal: start closing the dialog', async () => {
            const componentAsAny = component as any;
            componentAsAny.dialogRef = {
                close: () => { }
            }
            spyOn(componentAsAny.dialogRef, 'close')
            component.closeModal();
            expect(componentAsAny.dialogRef.close).toHaveBeenCalled();
        });
        it('ngOnInit.validateFinisings: validate initialization flow with finishings', async () => {
            component.part = new Part();
            component.finishings = new Map<string, string[]>();
            component.materials = []
            component.colors = []
            jasmine.clock().install();
            component.ngOnInit(); // Remember that components run the ngOnInit when nstantiated by the testbed.
            jasmine.clock().tick(200);
            const data = component.finishings;
            // console.log('[ngOnInit.validateFinisings]> finishings: ' + JSON.stringify(data))
            expect(data).toBeDefined('Check that finishing is not empty');
            expect(data.size).toBe(3, 'The number of materials does not match');
            console.log('[ngOnInit.validateFinisings]> materials: ' + component.materials)
            expect(component.materials.length).toBe(3, 'The number of materials does not match');
            const obtained = component.finishings.get('PLA')
            console.log('[ngOnInit.validateFinisings]> obtained: ' + obtained)
            expect(obtained.length).toBe(11, 'The number of colors does not match');
            jasmine.clock().uninstall()
        });
    });
});
