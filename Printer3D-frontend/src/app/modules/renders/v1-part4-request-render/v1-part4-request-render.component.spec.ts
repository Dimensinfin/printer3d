// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
// - TESTING
import { inject } from '@angular/core/testing';
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
// - DOMAIN
import { V1Part4RequestRenderComponent } from './v1-part4-request-render.component';
import { Part4Request } from '@domain/Part4Request.domain';

xdescribe('COMPONENT V1Part4RequestRenderComponent [Module: RENDER]', () => {
    let component: V1Part4RequestRenderComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
                V1Part4RequestRenderComponent
            ],
            providers: [
            ]
        }).compileComponents();

        const fixture = TestBed.createComponent(V1Part4RequestRenderComponent);
        component = fixture.componentInstance;
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            const componentAsAny = component as any;
            expect(component).toBeDefined('component has not been created.');
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getter - contract', () => {
            const testPart: Part4Request = new Part4Request({
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
                "active": true
            });
            expect(component.getNode()).toBeUndefined()
            component.node = testPart;
            expect(component.getUniqueId()).toBe("0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2");
            expect(component.getLabel()).toBe("Boquilla Ganesha");
            expect(component.getMaterial()).toBe("PLA");
            expect(component.getColor()).toBe("GRIS");
            expect(component.getRequired()).toBe(0);
            testPart.setRequired(4)
            expect(component.getRequired()).toBe(4);
            expect(component.getMissing()).toBe(3);
        });
    });
    describe('Code Coverage Phase [Methods]', () => {
        it('removePart: pass the remove part event to the parent', () => {
            const componentAsAny = component as any
            componentAsAny.container = {
                removePart: () => { }
            }
            spyOn(componentAsAny.container, 'removePart')
            component.removePart()
            expect(componentAsAny.container.removePart).toHaveBeenCalled()
        });
    });
});
