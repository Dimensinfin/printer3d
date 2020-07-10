// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Injectable } from '@angular/core';
import { ErrorHandler } from '@angular/core';
import { Injector } from '@angular/core';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartContainer } from '@domain/PartContainer.domain';
import { environment } from '@env/environment';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { BackgroundEnabledComponent } from './modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { IsolationService } from './platform/isolation.service';
import { HttpErrorResponse } from '@angular/common/http';
// import * as Rollbar from 'rollbar';
// import Rollbar from 'rollbar';
import { RollbarService } from 'angular-rollbar';

// export const rollbarConfig = {
//     accessToken: '4b7515a4ac41496b931963f64ef666e2',
//     captureUncaught: true,
//     captureUnhandledRejections: true,
// };

@Injectable()
export class AppErrorHandler implements ErrorHandler {
    // constructor(@Inject(RollbarService) private rollbar: Rollbar) { }
    constructor(private rollbar: RollbarService) { }

    handleError(err: any): void {
        // let rollbar = this.injector.get(RollbarService);
        // let appStoreService = this.injector.get(AppStoreService);
        // let isolationservice = this.injector.get(IsolationService);
        // if (err instanceof HttpErrorResponse) {
        //     const errorInfo: string = err.error.errorInfo
        //     const httpStatus: string = err.error.httpStatus
        //     const message: string = this.messageMap(err.error)
        //     isolationservice.errorNotification(message, errorInfo)
        // }
        // Use type checking to detect the different types of errors.
        // if (err.constructor.name === 'TypeError') {
        //   // Those are syntax exceptions detected on the runtime.
        //   console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
        //   if (environment.showexceptions)
        //     appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
        this.rollbar.error(err);
        // } else if (err.constructor.name === 'Error') {
        //   // Those are syntax exceptions detected on the runtime.
        //   console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
        //   if (appStoreService.showExceptions())
        //     appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
        //   rollbar.error(err);
        // } else {
        //   console.log('ERR[RollbarErrorHandler.handleError]> Error intercepted: ' + JSON.stringify(err.message));
        //   if (appStoreService.showExceptions())
        //     appStoreService.errorNotification(err.message, 'EXCEPCION ' + err.status)
        //   rollbar.error(err);
        // }
    }
    private messageMap(exception: any): string {
        const messageMapped: boolean = false
        return 'Excepcion de tipo [' + exception.errorInfo + '] durante la ultima operacion.'
    }
}

// export function rollbarFactory() {
//   return new Rollbar(rollbarConfig);
// }
// export const RollbarService = new InjectionToken<Rollbar>('rollbar');
