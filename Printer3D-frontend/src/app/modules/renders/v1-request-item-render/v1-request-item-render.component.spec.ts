// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { Printer3DConstants } from '../../../platform/Printer3DConstants.platform';
// - TESTING
import { async } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - DOMAIN
import { V1RequestItemRenderComponent } from './v1-request-item-render.component';
import { RequestItem } from '@domain/production/RequestItem.domain';
import { Part } from '@domain/inventory/Part.domain';
import { Model } from '@domain/inventory/Model.domain';

describe('COMPONENT V1RequestItemRenderComponent [Module: RENDER]', () => {
    let component: V1RequestItemRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1RequestItemRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1RequestItemRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            expect(component).toBeDefined('component has not been created.');
        });
    });
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Getters]', () => {
        it('getters: validate getter contract for Part', () => {
            const coil = new RequestItem({
                "itemId": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "type": "PART",
                "quantity": 4,
                "missing": 1,
                required: 3,
                content: new Part({
                    label: '-TEST-LABEL-',
                    price: 4,
                    material: 'PLA',
                    color: 'BLUE'
                })
            })
            const coilAsAny = coil as any
            component.node = coil
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.hasMissing()).toBeTrue()
            expect(component.getMissing()).toBe('1')
            coilAsAny.missing = 0
            expect(component.getMissing()).toBe('-')
            expect(component.getRequired()).toBe(4)
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getPrice()).toBe('16 €')
            expect(component.getMaterial()).toBe('PLA')
            expect(component.getColor()).toBe('BLUE')
        });
        it('getters: validate getter contract for Model', () => {
            const coil = new RequestItem({
                "itemId": "9903926b-e786-4fb2-8e8e-68960ebebb7a",
                "type": "MODEL",
                "quantity": 4,
                "missing": 1,
                required: 3,
                content: new Model({
                    label: '-TEST-LABEL-',
                    price: 4
                })
            })
            const coilAsAny = coil as any
            component.node = coil
            expect(component).toBeDefined();
            expect(component.getNode()).toBeDefined()
            expect(component.getUniqueId()).toBe("9903926b-e786-4fb2-8e8e-68960ebebb7a")
            expect(component.hasMissing()).toBeTrue()
            expect(component.getMissing()).toBe('1')
            coilAsAny.missing = 0
            expect(component.getMissing()).toBe('-')
            expect(component.getRequired()).toBe(4)
            expect(component.getLabel()).toBe('-TEST-LABEL-')
            expect(component.getPrice()).toBe('16 €')
            expect(component.getMaterial()).toBe('-')
            expect(component.getColor()).toBe('-')
        });
    });
    describe('Coverage Phase [Methods]', () => {
        it('removeContent: send the event to remove a content', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                removeContent: () => { }
            }
            spyOn(componentAsAny.container, 'removeContent')
            component.removeContent()
            expect(componentAsAny.container.removeContent).toHaveBeenCalled()
        });
    });
});
