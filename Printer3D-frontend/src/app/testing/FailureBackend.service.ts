// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - SERVICES
import { SupportHttpClientWrapperService } from './SupportHttpClientWrapperService.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/inventory/Part.domain';
import { Coil } from '@domain/inventory/Coil.domain';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { MachineV2 } from '@domain/production/MachineV2.domain';
import { RequestForm } from '@domain/RequestForm.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';
import { HttpErrorResponse } from '@angular/common/http';
import { WeekAmount } from '@domain/dto/WeekAmount.dto';

export class FailureBackendService {
    public type: string = 'FAILURE'
    public apiMachinesStartBuild_v2(machineId: string, jobRequest: JobRequest, transformer: ResponseTransformer): Observable<MachineV2> {
        return Observable.create((observer) => {
            const exception = new HttpErrorResponse({
                error: {
                    status: 500,
                    errorName: "EXCEPTION",
                    httpStatus: "500 EXCEPTION",
                    message: "-EXCEPTION-MESSAGE-"
                }
            })
            observer.error(exception);
        });
    }
    public apiMachinesCancelBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<MachineV2> {
        return Observable.create((observer) => {
            const exception = new HttpErrorResponse({
                error: {
                    status: 500,
                    errorName: "EXCEPTION",
                    httpStatus: "500 EXCEPTION",
                    message: "-EXCEPTION-MESSAGE-"
                }
            })
            observer.error(exception);
        });
    }
    public apiMachinesCompleteBuild_v1(machineId: string, transformer: ResponseTransformer): Observable<MachineV2> {
        return Observable.create((observer) => {
            const exception = new HttpErrorResponse({
                error: {
                    status: 500,
                    errorName: "EXCEPTION",
                    httpStatus: "500 EXCEPTION",
                    message: "-EXCEPTION-MESSAGE-"
                }
            })
            observer.error(exception);
        });
    }
}
