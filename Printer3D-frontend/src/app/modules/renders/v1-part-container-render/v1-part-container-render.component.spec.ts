// - CORE
import { NO_ERRORS_SCHEMA, ChangeDetectorRef } from '@angular/core'
// - TESTING
import { async } from '@angular/core/testing'
import { fakeAsync } from '@angular/core/testing'
import { tick } from '@angular/core/testing'
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
import { V1PartContainerRenderComponent } from './v1-part-container-render.component'
import { PartContainer } from '@domain/inventory/PartContainer.domain'
import { EVariant } from '@domain/interfaces/EPack.enumerated'

describe('COMPONENT V1PartContainerRenderComponent [Module: RENDER]', () => {
    let component: V1PartContainerRenderComponent

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                V1PartContainerRenderComponent
            ],
            providers: [
                { provide: ToastrService, useClass: SupportToastrService },
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: DialogFactoryService, useValue: {} },
                { provide: ChangeDetectorRef, useValue: { detectChanges: () => { } } },
            ]
        }).compileComponents()

        const fixture = TestBed.createComponent(V1PartContainerRenderComponent)
        component = fixture.componentInstance
    }))

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.')
        })
    })

    // - C O D E   C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters: validate getter contract for Part', () => {
            const partContainer = new PartContainer({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "buildTime": 90,
                "weight": 6,
                "imagePath": '-IMAGE-',
                "modelPath": '-MODEL-PATH-',
                contents: [
                    new Part({
                        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "color": "GRIS",
                        "buildTime": 90,
                        "weight": 6,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": 'image',
                        "modelPath": null,
                        "active": true
                    })
                ]
            })
            component.node = partContainer
            expect(component).toBeDefined()
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("part-container:0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2")
            expect(component.getLabel()).toBe("Boquilla Ganesha")
            expect(component.getDescription()).toBe("Boquilla Ganesha")
            expect(component.getBuildTime()).toBe('90 min.')
            expect(component.getWeigth()).toBe('6 gr.')
            expect(component.getImagePath()).toBe('-IMAGE-')
            expect(component.getModelPath()).toBe('-MODEL-PATH-')
            expect(component.imagePathVisible()).toBeTrue()
            partContainer.imagePath = undefined
            expect(component.imagePathVisible()).toBeFalse()
            expect(component.modelVisible()).toBeTrue()
            partContainer.modelPath = undefined
            expect(component.modelVisible()).toBeFalse()
        })
    })
    describe('Coverage Phase [Editing]', () => {
        it('isEditing: check the "editing" flag', () => {
            expect(component.isEditing()).toBeFalse()
        })
        it('toggleEdition.noEvent: check the change on the editing', () => {
            const partContainer = new PartContainer({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "buildTime": 90,
                "weight": 6,
                "imagePath": '-IMAGE-',
                "modelPath": '-MODEL-PATH-',
                contents: [
                    new Part({
                        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "color": "GRIS",
                        "buildTime": 90,
                        "weight": 6,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": 'image',
                        "modelPath": null,
                        "active": true
                    })
                ]
            })
            component.node = partContainer
            component.toggleEdition()
            expect(component.editing).toBeTrue()
            component.toggleEdition()
            expect(component.editing).toBeFalse()
            component.toggleEdition()
            expect(component.editing).toBeTrue()
        })
        it('toggleEdition.event: check the change on the editing', () => {
            const partContainer = new PartContainer({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "buildTime": 90,
                "weight": 6,
                "imagePath": '-IMAGE-',
                "modelPath": '-MODEL-PATH-',
                contents: [
                    new Part({
                        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "color": "GRIS",
                        "buildTime": 90,
                        "weight": 6,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": 'image',
                        "modelPath": null,
                        "active": true
                    })
                ]
            })
            component.node = partContainer
            component.toggleEdition({ event: 'event', stopPropagation: () => { } })
            expect(component.editing).toBeTrue()
            component.toggleEdition()
            expect(component.editing).toBeFalse()
            component.toggleEdition()
            expect(component.editing).toBeTrue()
        })
        it('saveEditing: save the edited model properties', async () => {
            jasmine.clock().install()
            const partContainer = new PartContainer({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Boquilla Ganesha",
                "description": "Boquilla Ganesha",
                "buildTime": 90,
                "weight": 6,
                "imagePath": '-IMAGE-',
                "modelPath": '-MODEL-PATH-',
                contents: [
                    new Part({
                        "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                        "label": "Boquilla Ganesha",
                        "description": "Boquilla Ganesha",
                        "material": "PLA",
                        "color": "GRIS",
                        "buildTime": 90,
                        "weight": 6,
                        "cost": 1.0,
                        "price": 6.0,
                        "stockLevel": 5,
                        "stockAvailable": 0,
                        "imagePath": 'image',
                        "modelPath": null,
                        "active": true
                    })
                ]
            })
            component.node = partContainer
            const componentAsAny = component as any
            component.editPart = new Part({
                "id": "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2",
                "label": "Slot Car",
                "description": "Descripcion de una base de slot",
                "buildTime": 120,
                "weight": 8,
                "imagePath": 'image',
                "modelPath": null
            })
            expect(componentAsAny.backendConnections).toBeDefined()
            expect(componentAsAny.backendConnections.length).toBe(0)
            expect(component.editing).toBeFalse()
            expect(component.variant).toBe(EVariant.DEFAULT)
            expect(component.editPart.description).toBe("Descripcion de una base de slot")
            await component.saveEditing()
            jasmine.clock().tick(1100)
            expect(component.variant).toBe(EVariant.CATALOG)
            expect(component.getDescription()).toBe("Descripcion de una base de slot")
            expect(component.getBuildTime()).toBe('120 min.')
            expect(component.getWeigth()).toBe('8 gr.')
            expect(component.getImagePath()).toBe('image')
            expect(component.getModelPath()).toBeNull()
            jasmine.clock().uninstall()
        })
    })
})
