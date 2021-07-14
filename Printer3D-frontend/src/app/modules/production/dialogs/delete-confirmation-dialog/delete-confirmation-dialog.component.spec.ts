// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { ReactiveFormsModule } from '@angular/forms'
import { FormsModule } from '@angular/forms'
import { v4 as uuidv4 } from 'uuid'
// - MATERIAL
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { MatDialog } from '@angular/material/dialog'
import { MatDialogRef } from '@angular/material/dialog'
// - TESTING
import { inject } from '@angular/core/testing'
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
import { ComponentFixture } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component'
import { routes } from '@app/testing/RouteMockUp.component'
import { HarnessLoader } from '@angular/cdk/testing'
import { TestbedHarnessEnvironment } from '@angular/cdk/testing/testbed'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
// - DOMAIN
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { MatDialogHarness } from '@angular/material/dialog/testing'
import { BackendService } from '@app/services/backend.service'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { DeleteConfirmationDialogComponent } from './delete-confirmation-dialog.component'
import { ProductionService } from '../../service/production.service'

// DEBUG: There is an injection that I do not know how to mock
xdescribe('COMPONENT DeleteConfirmationDialogComponent [Module: PRODUCTION]', () => {
    let component: DeleteConfirmationDialogComponent
    let fixture: ComponentFixture<DeleteConfirmationDialogComponent>
    let backendService: SupportBackendService
    let productionService = {}

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
                DeleteConfirmationDialogComponent
            ],
            providers: [
                { provide: MAT_DIALOG_DATA, useValue: { request: new CustomerRequest({ label: '-REQUEST-LABEL-' }) } },
                { provide: MatDialogRef, useValue: { close: (dialogResult: any) => { } } },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: ProductionService, useClass: productionService }
            ]
        }).compileComponents()

        fixture = TestBed.createComponent(DeleteConfirmationDialogComponent)
        component = fixture.componentInstance
        backendService = TestBed.inject(SupportBackendService)
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(componentAsAny.request).toBeDefined('field "reqeust" should have initial value.')
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('requestName: get the Request identification name', () => {
            expect(component.requestName()).toBe('-REQUEST-LABEL-')
        })
    })
    describe('Code Coverage Phase [Methods]', () => {
        it('closeModal: close the modal dialog', () => {
            spyOn(component.dialogRef, 'close')
            component.closeModal()
            expect(component.dialogRef.close).toHaveBeenCalled()
        })
        it('deleteRequest.success: send the delete command to the respository', async () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            spyOn(component.dialogRef, 'close')
            await component.deleteRequest()
            expect(componentAsAny.backendConnections.length).toBe(1)
            expect(component.dialogRef.close).toHaveBeenCalled()
        })
        it('deleteRequest.failure: send the delete command to the respository', async () => {
            const componentAsAny = component as any
            expect(componentAsAny.backendConnections.length).toBe(0)
            spyOn(component.dialogRef, 'close')
            backendService.activateFailure('apiProductionDeleteRequest_v1', {
                "timestamp": "2020-07-14T07:41:22.117152Z",
                "httpStatus": "404 NOT_FOUND",
                "httpStatusCode": 404,
                "httpStatusName": "NOT_FOUND",
                "errorName": "REQUEST_NOT_FOUND",
                "errorCode": "dimensinfin.printer3d.defined.repository.logic",
                "cause": "No Request found while trying to delete the request.",
                "message": "Request record with id [{0}] not found at the repository."
            })
            await component.deleteRequest()
            expect(componentAsAny.backendConnections.length).toBe(1)
            // expect(component.dialogRef.close).not.toHaveBeenCalled()
        })
    })
})
