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
    private printErrorReport(error: HttpErrorResponse) {
        let errorName: string = '-'
        let httpStatus
        let message: string = '-'
        let cause: string = '-'
        if (null != error.error) {
            if (null != error.error.errorName) {
                errorName = error.error.errorName
                httpStatus = error.error.httpStatus
                message = error.error.message
                cause = error.error.cause
            }
        }
        else {
            errorName = error.statusText
            httpStatus = error.status
            message = error.message
            cause = error.url
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
}
