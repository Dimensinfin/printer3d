// - CORE
import { Injectable } from '@angular/core';
import { Injector } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { retry } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
// - DOMAIN
import { IsolationService } from './platform/isolation.service';

@Injectable({ providedIn: 'root' })
export class HttpErrorInterceptor implements HttpInterceptor {
    private isolationService: IsolationService
    constructor(private injector: Injector) {
        this.isolationService = this.injector.get(IsolationService);
    }
    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {
                    this.printErrorReport(error)
                    console.log('-[HttpErrorInterceptor.intercept.catchError]> Error:' + JSON.stringify(error))
                    return throwError(error);
                })
            )
    }
    private printErrorReport(exception: HttpErrorResponse) {
        // Detect the type of error to extract the corrent information.
        if (exception.error) {
            const error = exception.error
            if (error.error) { // This type of exception is received when there is a parsing error.
                const causeException = error.error
                if (causeException instanceof SyntaxError) {
                    this.processSyntaxErrorReport(exception)
                    return
                }
            }
        }
        let errorName: string = '-'
        let httpStatus
        let message: string = '-'
        let cause: string = '-'
        if (null != exception.error) {
            if (null != exception.error.errorName) {
                errorName = exception.error.errorName
                httpStatus = exception.error.httpStatus
                message = exception.error.message
                cause = exception.error.cause
            }
        }
        else {
            errorName = exception.statusText
            httpStatus = exception.status
            message = exception.message
            cause = exception.url
        }
        console.log('>[Exception]> ErrorName: ' + errorName)
        console.log('>[Exception]> HttpStatus: ' + httpStatus)
        console.log('>[Exception]> Message: ' + message)
        if (null != cause) console.log('>[Exception]> Cause: ' + cause)
        if (null != cause)
            this.isolationService.errorNotification(message + '\nCausa: ' + cause, '[' + httpStatus + ']/' + errorName)
        else
            this.isolationService.errorNotification(message, '[' + httpStatus + ']/' + errorName)
    }
    private processSyntaxErrorReport(exception: HttpErrorResponse): void {
        const message = exception.message
        const cause = exception.error.error.message
        const errorName = 'SYNTAX EXCEPTION'
        console.log('>[Exception]> ErrorName: ' + errorName)
        console.log('>[Exception]> Message: ' + message)
        console.log('>[Exception]> Cause: ' + cause)
        this.isolationService.errorNotification(message + '\nCausa: ' + cause, '[' + errorName + ']')
    }
}
