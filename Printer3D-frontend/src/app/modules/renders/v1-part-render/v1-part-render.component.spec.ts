// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core'
import { Observable } from 'rxjs'
import { ChangeDetectorRef } from '@angular/core'
// - TESTING
import { fakeAsync, tick, waitForAsync } from '@angular/core/testing'
import { TestBed } from '@angular/core/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { routes } from '@app/testing/RouteMockUp.component'
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service'
// - DOMAIN
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { Part } from '@domain/inventory/Part.domain'
import { V1PartRenderComponent } from './v1-part-render.component'
import { FormsModule } from '@angular/forms'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'

describe('COMPONENT V1PartRenderComponent [Module: RENDER]', () => {
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
    let component: V1PartRenderComponent
    let isolationService = { setToStorageObject: () => { } }
    let inventoryService = { apiv1_InventoryUpdatePart: () => { } }
    let dialogFactory = { processClick: () => { } }

    beforeEach(waitForAsync(() => {
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
                { provide: IsolationService, useValue: isolationService },
                { provide: InventoryService, useValue: inventoryService },
                { provide: DialogFactoryService, useValue: dialogFactory },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1PartRenderComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            expect(component).toBeDefined('component has not been created.')
        })
        it('Initial state', () => {
            const componentAsAny = component as any
            expect(component).toBeDefined('component has not been created.')
            expect(component.editPart).toBeDefined()
            expect(component.editing).toBeFalse()
            expect(componentAsAny.backendConnections).toBeDefined()
            expect(componentAsAny.backendConnections.length).toBe(0)
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
    describe('Coverage Phase [Editing]', () => {
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse()
        })
        it('toggleEdition: check the change on the editing', () => {
            component.node = testPartActive
            component.toggleEdition()
            expect(component.editing).toBeTrue()
            component.toggleEdition()
            expect(component.editing).toBeFalse()
            component.toggleEdition()
            expect(component.editing).toBeTrue()
        })
    })
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('saveEditing: check the save flow', fakeAsync(() => {
            component.node = testPartActive
            const updatePart = new Part({
                "id": "00000b78a-8eb7-4d53-8ada-b5ae3bfdafff",
                "label": "Boquilla Ganesha Editada",
                "description": "Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 190,
                "cost": 10.0,
                "price": 60.0,
                "stockLevel": 15,
                "stockAvailable": 5,
                "imagePath": null,
                "modelPath": null,
                "active": true
            })
            component.editPart = updatePart
            expect(component.getUniqueId()).toBe("0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2")
            expect(component.getLabel()).toBe("Boquilla Ganesha")
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("GRIS")
            expect(component.getCost()).toBe('1 €')
            expect(component.getPrice()).toBe('6 €')
            expect(component.getStockRequired()).toBe(5)
            expect(component.getStockAvailable()).toBe(0)
            console.log('part state: '+ component.getActive())
            // expect(component.getActive()).toBe('ACTIVA')
            spyOn(inventoryService, 'apiv1_InventoryUpdatePart').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(new Part(updatePart))
                        }, 100)
                    })
                })
            component.editing = true
            expect(component.editing).toBeTrue()
            component.saveEditing()
            tick(3000)
            expect(component.getUniqueId()).toBe("00000b78a-8eb7-4d53-8ada-b5ae3bfdafff")
            expect(component.getLabel()).toBe("Boquilla Ganesha Editada")
            expect(component.getCost()).toBe('10 €')
            expect(component.getPrice()).toBe('60 €')
            expect(component.getStockRequired()).toBe(15)
            expect(component.getStockAvailable()).toBe(5)
            console.log('part state: '+ component.getActive())
            expect(component.getActive()).toBe('ACTIVA')
        }))
        it('saveEditing: validate the reinitialization of stock level on inactive', fakeAsync(() => {
            component.node = testPartActive
            const updatePart = new Part({
                "id": "00000b78a-8eb7-4d53-8ada-b5ae3bfdafff",
                "label": "Boquilla Ganesha Editada",
                "description": "Boquilla Ganesha",
                "material": "PLA",
                "color": "GRIS",
                "buildTime": 190,
                "cost": 10.0,
                "price": 60.0,
                "stockLevel": 15,
                "stockAvailable": 5,
                "imagePath": null,
                "modelPath": null,
                "active": false
            })
            component.editPart = updatePart
            expect(component.getUniqueId()).toBe("0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2")
            expect(component.getLabel()).toBe("Boquilla Ganesha")
            expect(component.getMaterial()).toBe("PLA")
            expect(component.getColor()).toBe("GRIS")
            expect(component.getCost()).toBe('1 €')
            expect(component.getPrice()).toBe('6 €')
            expect(component.getStockRequired()).toBe(5)
            expect(component.getStockAvailable()).toBe(0)
            console.log('part state: '+ component.getActive())
            // expect(component.getActive()).toBe('ACTIVA')
            spyOn(inventoryService, 'apiv1_InventoryUpdatePart').and
                .callFake(function () {
                    return new Observable(observer => {
                        setTimeout(() => {
                            observer.next(new Part(updatePart))
                        }, 100)
                    })
                })
            component.editing = true
            expect(component.editing).toBeTrue()
            component.saveEditing()
            tick(3000)
            expect(component.getUniqueId()).toBe("00000b78a-8eb7-4d53-8ada-b5ae3bfdafff")
            expect(component.getLabel()).toBe("Boquilla Ganesha Editada")
            expect(component.getCost()).toBe('10 €')
            expect(component.getPrice()).toBe('60 €')
            expect(component.getStockRequired()).toBe(0)
            expect(component.getStockAvailable()).toBe(5)
            console.log('part state: '+ component.getActive())
            expect(component.getActive()).toBe('FUERA PROD.')
        }))
        it('duplicatePart: generate the event to open the new part dialog', fakeAsync(() => {
            const componentAsAny = component as any
            spyOn(dialogFactory, 'processClick').and
                .callFake(function () {
                    return {
                        afterClosed: () => {
                            return new Observable(observer => {
                                setTimeout(() => {
                                    observer.next('-RESULT-')
                                }, 100)
                            })
                        }
                    }
                })
            spyOn(isolationService, 'setToStorageObject')
            component.duplicatePart()
            tick(1000)
            tick(1000)
            expect(isolationService.setToStorageObject).toHaveBeenCalled()
            expect(dialogFactory.processClick).toHaveBeenCalled()
        }))
    })
})
