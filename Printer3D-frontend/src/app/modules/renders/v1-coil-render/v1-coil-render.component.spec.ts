// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
import { Subject } from 'rxjs'
import { Router } from '@angular/router'
import { Printer3DConstants } from '../../../platform/Printer3DConstants.platform'
import { ChangeDetectionStrategy } from '@angular/core'
import { ChangeDetectorRef } from '@angular/core'
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
// - PROVIDERS
import { ToastrService } from 'ngx-toastr'
import { SupportToastrService } from '@app/testing/SupportToastrService.service'
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { BackendService } from '@app/services/backend.service'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service'
// - DOMAIN
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { V1DockComponent } from '../../common/v1-dock/v1-dock.component'
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component'
import { Part } from '@domain/inventory/Part.domain'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { FormsModule } from '@angular/forms'
import { V1CoilRenderComponent } from './v1-coil-render.component'
import { Coil } from '@domain/inventory/Coil.domain'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'
import { SupportInventoryService } from '@app/testing/SupportInventory.service'
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http'

describe('COMPONENT V1CoilRenderComponent [Module: RENDER]', () => {
    const coil = new Coil({
        "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
        "material": "PLA",
        "color": "RED",
        "weight": 800,
        "label": "-LABEL-"
    })
    let component: V1CoilRenderComponent
    let fixture: any
    let isolationService: SupportIsolationService = new SupportIsolationService()
    let inventoryService: SupportInventoryService = new SupportInventoryService()

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1CoilRenderComponent
            ],
            providers: [
                { provide: IsolationService, useValue: isolationService },
                { provide: InventoryService, useValue: inventoryService },
                { provide: DialogFactoryService, useValue: {} },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } }
            ]
        }).compileComponents()

        fixture = TestBed.createComponent(V1CoilRenderComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            expect(component.editCoil).toBeDefined()
            expect(component.editing).toBeFalse()
        })
    })

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getNode.success: check the return type for the underlying node', () => {
            component.node = coil
            expect(component).toBeDefined()
            expect(component.getNode()).toBeDefined()
            expect(component.getNode() instanceof Coil).toBeTrue()
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe('RED')
            expect(component.getWeight()).toBe('800 gr.')
        })
        it('getNode.failure: check the return type for the underlying node', () => {
            expect(component.getNode()).toBeUndefined()
        })
        it('getUniqueId.success: check the value for the unique identifier', () => {
            component.node = coil
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
        })
        it('getUniqueId.failure: check the value for the unique identifier', () => {
            expect(component.getUniqueId()).toBe('-')
        })
        it('getMaterial.success: check the value for thematerial', () => {
            component.node = coil
            expect(component.getMaterial()).toBe("PLA")
        })
        it('getMaterial.failure: check the value for the unique identifier', () => {
            expect(component.getMaterial()).toBe('-')
        })
        it('getColor.success: check the value for thematerial', () => {
            component.node = coil
            expect(component.getColor()).toBe('RED')
        })
        it('getColor.failure: check the value for the unique identifier', () => {
            expect(component.getColor()).toBe('-')
        })
        it('getWeight.success: check the value for thematerial', () => {
            component.node = coil
            expect(component.getWeight()).toBe('800 gr.')
        })
        it('getWeight.failure: check the value for the unique identifier', () => {
            expect(component.getWeight()).toBe('- gr.')
        })
        it('getLabel.success: check the value for thematerial', () => {
            component.node = coil
            expect(component.getLabel()).toBe("-LABEL-")
        })
        it('getLabel.failure: check the value for the unique identifier', () => {
            expect(component.getLabel()).toBe('-')
        })
    })
    describe('Coverage Phase [Editing]', () => {
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse()
        })
        it('toggleEdition: check the change on the editing', () => {
            component.node = coil
            component.toggleEdition()
            expect(component.editing).toBeTrue()
            component.toggleEdition()
            expect(component.editing).toBeFalse()
            component.toggleEdition()
            expect(component.editing).toBeTrue()
        })
        it('saveEditing.success check the save flow', fakeAsync(() => {
            component.node = coil
            const updateCoil = new Coil({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "material": "PLA",
                "color": "ROJO",
                "weight": 600,
                "label": "-NEW-LABEL-"
            })
            component.editCoil = updateCoil
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("RED")
            expect(component.getWeight()).toBe('800 gr.')
            expect(component.getLabel()).toBe('-LABEL-')
            spyOn(inventoryService, 'apiv2_InventoryUpdateCoil').and
                .callFake(function () {
                    return inventoryService.prepareResponse('inventory.update.coil', updateCoil)
                })
            component.editing = true
            expect(component.editing).toBeTrue()
            component.saveEditing()
            tick(1000)
            expect(component.editing).toBeFalse()
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("ROJO")
            expect(component.getWeight()).toBe('600 gr.')
            expect(component.getLabel()).toBe("-NEW-LABEL-")
        }))
        it('saveEditing.failure check the save flow', fakeAsync(() => {
            component.node = coil
            const updateCoil = new Coil({
                "id": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "material": "PLA",
                "color": "ROJO",
                "weight": 600,
                "label": "-NEW-LABEL-"
            })
            component.editCoil = updateCoil
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("RED")
            expect(component.getWeight()).toBe('800 gr.')
            expect(component.getLabel()).toBe('-LABEL-')
            spyOn(inventoryService, 'apiv2_InventoryUpdateCoil').and
                .callFake(function () {
                    return inventoryService.prepareResponse('Throw Error', new HttpErrorResponse({
                        error: 'This is the error message',
                        headers: new HttpHeaders(),
                        status: 401,
                        statusText: "NOT_FOUND",
                        url: "url"
                    }))
                })
            spyOn(isolationService, 'processException')
            component.editing = true
            expect(component.editing).toBeTrue()
            component.saveEditing()
            tick(1000)
            expect(component.editing).toBeTrue()
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("RED")
            expect(component.getWeight()).toBe('800 gr.')
            expect(component.getLabel()).toBe('-LABEL-')
            expect(isolationService.processException).toHaveBeenCalled()
        }))
    })
})
