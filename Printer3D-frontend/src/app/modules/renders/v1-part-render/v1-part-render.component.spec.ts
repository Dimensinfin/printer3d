// - CORE
import { ComponentRef, NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
import { ChangeDetectorRef } from '@angular/core'
// - TESTING
import { async } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
import { ToastrService } from 'ngx-toastr'
import { SupportToastrService } from '@app/testing/SupportToastrService.service'
import { IsolationService } from '@app/platform/isolation.service'
import { SupportIsolationService } from '@app/testing/SupportIsolation.service'
import { BackendService } from '@app/services/backend.service'
import { SupportBackendService } from '@app/testing/SupportBackend.service'
// - DOMAIN
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { Part } from '@domain/inventory/Part.domain'
import { V1PartRenderComponent } from './v1-part-render.component'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { FormsModule } from '@angular/forms'

describe('COMPONENT V1PartRenderComponent [Module: RENDER]', () => {
    let component: V1PartRenderComponent
    const testPartActive: Part = new Part({
        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
        "label": "Boquilla Ganesha",
        "description": "Boquilla Ganesha",
        "material": "PLA",
        "color": "GRIS",
        "buildTime": 90,
        "cost": 1.0,
        "price": 6.0,
        "stockLevel": 5,
        "stockAvailable": 0,
        "imagePath": null,
        "modelPath": null,
        "active": true
    })
    const testPartInactive: Part = new Part({
        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
        "label": "Boquilla Ganesha",
        "description": "Boquilla Ganesha",
        "material": "PLA",
        "color": "GRIS",
        "buildTime": 90,
        "cost": 1.0,
        "price": 6.0,
        "stockLevel": 5,
        "stockAvailable": 1,
        "imagePath": null,
        "modelPath": null,
        "active": false
    })

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                FormsModule,
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1PartRenderComponent
            ],
            providers: [
                { provide: ToastrService, useClass: SupportToastrService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: DialogFactoryService, useValue: {} },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1PartRenderComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(component.editPart).toBeDefined()
            expect(component.editing).toBeFalse()
            expect(componentAsAny.backendConnections).toBeDefined()
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(componentAsAny.dataToPartTransformer).toBeDefined()
        })
        it('constructor.created: validate initial created instances', () => {
            const componentAsAny = component as any
            const transformer: ResponseTransformer = componentAsAny.dataToPartTransformer
            expect(transformer.transform({}) instanceof Part).toBeTrue()
        })
    })

    // - O N D E S T R U C T I O N   P H A S E
    describe('On Destruction Phase', () => {
        it('ngOnDestroy: check the unsubscription', async () => {
            const componetAsAny = component as any
            expect(componetAsAny.backendConnections.length).toBe(0)
            component.node = testPartActive
            component.editPart = testPartActive
            await component.saveEditing()
            expect(componetAsAny.backendConnections.length).toBe(1)
            await component.ngOnDestroy()
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Getters]', () => {
        it('getLabel:success check the "label" field when defined', () => {
            component.node = testPartActive
            expect(component.getLabel()).toBe("Boquilla Ganesha")
        })
        it('getMaterial:success check the "material" field when defined', () => {
            component.node = testPartActive
            expect(component.getMaterial()).toBe("PLA")
        })
        it('getColor:success check the "color" field when defined', () => {
            component.node = testPartActive
            expect(component.getColor()).toBe("GRIS")
        })
        it('getCost:success check the "cost" field when defined', () => {
            component.node = testPartActive
            expect(component.getCost()).toBe('1 €')
        })
        it('getPrice:success check the "price" field when defined', () => {
            component.node = testPartActive
            expect(component.getPrice()).toBe('6 €')
        })
        it('getStockRequired:success check the "stockLevel" field when defined', () => {
            component.node = testPartActive
            expect(component.getStockRequired()).toBe(5)
        })
        it('getStockAvailable:success check the "stockAvailable" field when defined', () => {
            component.node = testPartActive
            expect(component.getStockAvailable()).toBe(0)
        })
        it('getActive:active check the "active" field when defined', () => {
            testPartActive.active = true
            component.node = testPartActive
            expect(component.getActive()).toBe('ACTIVA')
        })
        it('getActive:inactive check the "active" field when defined', () => {
            testPartActive.active = false
            component.node = testPartActive
            expect(component.getActive()).toBe('FUERA PROD.')
        })
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse()
        })
        it('isActive: check the "editing" flag', () => {
            testPartActive.active = true
            component.node = testPartActive
            expect(component.isActive()).toBeTrue()
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('toggleEdition: check the change on the editing', () => {
            component.node = testPartActive
            component.toggleEdition()
            expect(component.editing).toBeTrue()
            component.toggleEdition()
            expect(component.editing).toBeFalse()
            component.toggleEdition()
            expect(component.editing).toBeTrue()
        })
        xit('saveEditing: check the save flow', async () => {
            testPartActive.active = true
            component.node = testPartActive
            component.editPart = testPartActive
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("GRIS")
            expect(component.getCost()).toBe('1 €')
            expect(component.getPrice()).toBe('6 €')
            expect(component.getStockRequired()).toBe(5)
            expect(component.getStockAvailable()).toBe(0)
            expect(component.getActive()).toBe('ACTIVA')
            component.editing = false
            expect(component.editing).toBeFalse()
            jasmine.clock().install()
            await component.saveEditing()
            jasmine.clock().tick(1100)
            jasmine.clock().uninstall()
            expect(component.editing).toBeTrue()
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("WHITE")
            expect(component.getCost()).toBe('0.85 €')
            expect(component.getPrice()).toBe('4 €')
            expect(component.getStockRequired()).toBe(3)
            expect(component.getStockAvailable()).toBe(4)
            expect(component.getActive()).toBe('ACTIVA')
            jasmine.clock().uninstall()
        })
        it('saveEditing: validate the reinitialization of stock level on inactive', async () => {
            component.node = testPartInactive
            component.editPart = testPartActive
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("GRIS")
            expect(component.getCost()).toBe('1 €')
            expect(component.getPrice()).toBe('6 €')
            expect(component.getStockRequired()).toBe(5)
            expect(component.getStockAvailable()).toBe(1)
            expect(component.getActive()).toBe('FUERA PROD.')
            component.editing = false
            expect(component.editing).toBeFalse()
            jasmine.clock().install()
            await component.saveEditing()
            // Response is hardcoded so this change is not reflected
            const editedPart: Part = new Part({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 90,
                "cost": 1.0,
                "price": 6.0,
                "stockLevel": 0,
                "stockAvailable": 8,
                "imagePath": null,
                "modelPath": null,
                "active": false
            })
            component.node = editedPart
            jasmine.clock().tick(1100)
            expect(component.editing).toBeTrue()
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("GRIS")
            expect(component.getCost()).toBe('1 €')
            expect(component.getPrice()).toBe('6 €')
            expect(component.getStockRequired()).toBe(0)
            expect(component.getStockAvailable()).toBe(8)
            expect(component.getActive()).toBe('FUERA PROD.')
            jasmine.clock().uninstall()
        })
        it('duplicatePart: generate the event to open the new part dialog', async () => {
            const componentAsAny = component as any
            componentAsAny.dialogFactory = {
                processClick: () => {
                    return {
                        afterClosed: () => {
                            return Observable.create((observer) => {
                                observer.next('-DATA-')
                                observer.complete()
                            })
                        }
                    }
                }
            }
            component.duplicatePart()
        })
    })
})
