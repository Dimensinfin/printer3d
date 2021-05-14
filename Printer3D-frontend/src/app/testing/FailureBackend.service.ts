// - CORE
import { Observable } from 'rxjs';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { MachineV2 } from '@domain/production/MachineV2.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { HttpErrorResponse } from '@angular/common/http';

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
