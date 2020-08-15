// - CORE
import { Injectable } from '@angular/core';
import { ErrorHandler } from '@angular/core';
import { Injector } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
// - DOMAIN
import { RollbarService } from 'angular-rollbar';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { IsolationService } from './platform/isolation.service';

@Injectable()
export class AppErrorHandler implements ErrorHandler {
    private notifier: ToastrService
    constructor(private rollbar: RollbarService) { }
    /**
     * Process all errors reported by the application, being them typos, syntactic or functional errors.
     * The method should filter them and report on console what is expected for development fixing and registering onto Rollbar for application production
     * @param error the error detected by the application. This can be any type of data structure.
     */
    public handleError(error: any): void {
        // Register the exception
        if (environment.production)
            this.rollbar.error(error);
        // Process the error received to identify recoverable exceptions that can be reported to user.
        if (error instanceof Error) {
            console.log('>[Exception]> Message: ' + error.message)
        }
        if (error instanceof HttpErrorResponse) {
            if (null != error.error) {
                if (null != error.error.errorName) {
                    const errorName: string = error.error.errorName
                    const httpStatus: string = error.error.httpStatus
                    const message: string = error.error.message
                    const cause: string = error.error.cause
                    console.log('>[Exception]> ErrorName: ' + errorName)
                    console.log('>[Exception]> HttpStatus: ' + httpStatus)
                    console.log('>[Exception]> Message: ' + message)
                    if (null != cause) console.log('>[Exception]> Cause: ' + cause)
                }
            }
            else {
                const errorName: string = error.statusText
                const httpStatus: number = error.status
                const message: string = error.message
                const cause: string = error.url
                console.log('>[Exception]> ErrorName: ' + errorName)
                console.log('>[Exception]> HttpStatus: ' + httpStatus)
                console.log('>[Exception]> Message: ' + message)
                if (null != cause) console.log('>[Exception]> Cause: ' + cause)
            }
        }
    }
}
