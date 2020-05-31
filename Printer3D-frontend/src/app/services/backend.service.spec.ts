// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { platformconstants } from '../platform/platform-constants';
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
import { IsolationService } from '../platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from './app-store.service';
import { BackendService } from './backend.service';
// import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from './httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '../testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { ResponseTransformer } from './support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';

describe('SERVICE BackendService [Module: CORE]', () => {
    let service: BackendService;
    let isolationService: IsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        })
            .compileComponents();
        service = TestBed.get(BackendService);
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.none: validate initial state without constructor', () => {
            console.log('><[core/BackendService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Backend-API]', () => {
        it('apiInventoryParts_v1.default: get the list of Parts', async () => {
            service.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
                .setTransformation((entrydata: any): PartListResponse => {
                    return new PartListResponse(entrydata);
                }))
                .subscribe((response: PartListResponse) => {
                    expect(response).toBeDefined();
                    expect(response.count).toBe(2);
                    expect(response.parts.length).toBe(2);
                });
        });
        it('apiNewPart_v1.default: get the persisted part', async () => {
            const part = new Part();
            service.apiNewPart_v1(part, new ResponseTransformer().setDescription('Transforms data into Part.')
                .setTransformation((entrydata: any): Part => {
                    return new Part(entrydata);
                }))
                .subscribe((response: Part) => {
                    expect(response).toBeDefined();
                });
        });
        it('apiNewCoil_v1.default: get the persisted coils', async () => {
            const coil = new Coil();
            service.apiNewCoil_v1(coil, new ResponseTransformer().setDescription('Transforms data into Coil.')
                .setTransformation((entrydata: any): Coil => {
                    return new Coil(entrydata);
                }))
                .subscribe((response: Coil) => {
                    expect(response).toBeDefined();
                });
        });
        it('apiGetFinishings_v1.default: get the list of finishings available', async () => {
            service.apiGetFinishings_v1(new ResponseTransformer().setDescription('Transforms data into FinishingResponse.')
                .setTransformation((entrydata: any): FinishingResponse => {
                    return new FinishingResponse(entrydata);
                }))
                .subscribe((response: FinishingResponse) => {
                    expect(response).toBeDefined();
                });
        });
        it('apiInventoryCoils_v1.default: get the list of Coils', async () => {
            service.apiInventoryCoils_v1(new ResponseTransformer().setDescription('Transforms Inventory Coil list form backend.')
                .setTransformation((entrydata: any): CoilListResponse => {
                    return new CoilListResponse(entrydata);
                }))
                .subscribe((response: CoilListResponse) => {
                    expect(response).toBeDefined();
                    expect(response.count).toBe(3);
                    expect(response.coils.length).toBe(3);
                });
        });
        it('apiInventoryMachines_v1.default: get the list of Coils', async () => {
            service.apiInventoryMachines_v1(new ResponseTransformer().setDescription('Transforms Inventory Machine list form backend.')
                .setTransformation((entrydata: any): MachineListResponse => {
                    return new MachineListResponse(entrydata);
                }))
                .subscribe((response: MachineListResponse) => {
                    expect(response).toBeDefined();
                    expect(response.count).toBe(3);
                    expect(response.machines.length).toBe(3);
                });
        });
    });
});
