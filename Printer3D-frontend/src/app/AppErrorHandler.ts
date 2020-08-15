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
    private  notifier  : ToastrService
    constructor(
        private rollbar: RollbarService,
        private injector:Injector) { 
            setTimeout(() => this.notifier = injector.get(ToastrService));
        }
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
            const errorName: string = error.error.errorName
            const httpStatus: string = error.error.httpStatus
            const message: string = error.error.message
            const cause: string = error.error.cause
            if (null != cause)
                this.errorNotification(message + '\nCausa: ' + cause, '[' + httpStatus + ']/' + errorName)
            else
                this.errorNotification(message, '[' + httpStatus + ']/' + errorName)
            console.log('>[Exception]> ErrorName: ' + errorName)
            console.log('>[Exception]> HttpStatus: ' + httpStatus)
            console.log('>[Exception]> Message: ' + message)
            if (null != cause) console.log('>[Exception]> Cause: ' + cause)
        }
    }
    private notifierConfiguration: any = {
        autoDismiss: false,
        toastTimeout: 8000,
        newestOnTop: true,
        position: 'bottom-right',
        toastClass: 'notifier-box',
        titleClass: 'notifier-title',
        messageClass: 'notifier-message',
        animate: 'slideFromLeft'
    };
    public errorNotification(message: string, title?: string, options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != options) notConf = { ...this.notifierConfiguration, ...options };
        else notConf = this.notifierConfiguration;
        notConf.toastTimeout = 15000;
        this.notifier.error(message, title, notConf);
    }
}
