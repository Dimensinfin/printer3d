// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
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
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DialogFactoryService } from './dialog-factory.service';
import { MatDialog } from '@angular/material/dialog';

describe('COMPONENT DialogFactoryService [Module: CORE]', () => {
    let service: DialogFactoryService;
    let isolationService: SupportIsolationService;
    let dialog = { open: (dialog: any, config: any) => { } };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
            ],
            declarations: [
            ],
            providers: [
                { provide: MatDialog, useValue: dialog },
            ]
        }).compileComponents();

        service = TestBed.get(DialogFactoryService);
    }));

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.injection: validate initial state with injection', () => {
            expect(service).toBeDefined('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Dialog]', () => {
        it('processClick.empty: process the click action on a Feature', () => {
            const feature = new Feature();
            const serviceAsAny = service as any;
            spyOn(dialog, 'open');
            const dialogRef = service.processClick(feature);
            expect(dialog.open).not.toHaveBeenCalled();
            expect(dialogRef).toBeUndefined();
        });
        it('processClick.NewPartDialog: process the click action on a Feature', async () => {
            const feature = new Feature({
                "jsonClass": "Feature",
                "label": "/Nueva Pieza",
                "enabled": true,
                "active": false,
                "interaction": "DIALOG",
                "route": "NewPartDialog"
            });
            const serviceAsAny = service as any;
            spyOn(dialog, 'open');
            await service.processClick(feature);
            expect(dialog.open).toHaveBeenCalled();
        });
        it('processClick.NewCoilDialog: process the click action on a Feature', async () => {
            const feature = new Feature({
                "jsonClass": "Feature",
                "label": "/Nuevo Rollo",
                "enabled": true,
                "active": false,
                "interaction": "DIALOG",
                "route": "NewCoilDialog"
            });
            const serviceAsAny = service as any;
            spyOn(dialog, 'open');
            await service.processClick(feature);
            expect(dialog.open).toHaveBeenCalled();
        });
    });
});
