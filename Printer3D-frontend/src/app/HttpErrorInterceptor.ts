// - CORE
import { Injectable, Injector } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/internal/operators';
// - DOMAIN
import { IsolationService } from './platform/isolation.service';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';

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
        let errorName: string
        let httpStatus
        let message: string
        let cause: string
        // Detect the type of error to extract the corrent information.
        if (this.detectSyntaxError(exception)) {
            this.processSyntaxErrorReport(exception)
            return
        }
        if (this.detectSpringBootException(exception)) {
            errorName = exception.error.errorName
            httpStatus = exception.error.httpStatus
            message = exception.error.message
            cause = exception.error.cause
        }
        else {
            errorName = exception.statusText
            httpStatus = exception.status
            message = exception.message
        }
        console.log('>[Exception]> ErrorName: ' + errorName)
        console.log('>[Exception]> HttpStatus: ' + httpStatus)
        console.log('>[Exception]> Message: ' + message)
        if (cause) console.log('>[Exception]> Cause: ' + cause)
        if (cause)
            this.isolationService.errorNotification(message + '\nCausa: ' + cause, '[' + httpStatus + ']/' + errorName)
        else
            this.isolationService.errorNotification(message, '[' + httpStatus + ']/' + errorName)
    }
    private detectSyntaxError(exception: HttpErrorResponse): boolean {
        if (exception.error)
            if (exception.error.error) { // This type of exception is received when there is a parsing error.
                const causeException = exception.error.error
                if (causeException instanceof SyntaxError) return true
            }
        return false
    }
    private detectSpringBootException(exception: HttpErrorResponse): boolean {
        if (exception.error)
            if (null != exception.error.errorName) return true
        return false
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
