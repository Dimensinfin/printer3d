// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { environment } from '@env/environment';
// - HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';

@Injectable({
    providedIn: 'root'
})
export class HttpClientWrapperService {

    constructor(protected http: HttpClient) { }

    // -  H T T P   W R A P P E R S
    /**
     * This method wraps the HTTP access to the backend. It should add any predefined headers, any request specific headers and will also deal with mock data.
     * Mock data comes now into two flavours. The first one will search for the request on the list of defined requests (if the mock is active). If found then it will check if the request should be sent to the file system ot it should be resolved by accessing the LocalStorage.
     * @param  request [description]
     * @return         [description]
     */
    public wrapHttpGETCall(request: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpGETCall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.get(request, { headers: newheaders });
    }
    public wrapHttpPOSTCall(request: string, body: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpPOSTCall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.post(request, body, { headers: newheaders })
            .pipe(
                catchError(this.wrapHttpHandleError)
            );
    }
    /**
     * Reads a JSON formatted resource. There is no specific convertion to a types class and so can be done on the caller method.
     * @param request the location of the resource file to be read. The resource starts on the /assets/properties location.
     */
    public wrapHttpRESOURCECall(request: string): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpRESOURCECall]> request: " + request);
        return this.http.get(request);
    }

    /**
     * This is the common code to all secure calls. It will check if the call can use the mockup system and if that system has a mockup destionation for the request.
     * This call also should create a new set of headers to be used on the next call and should put inside the current authentication data.
     *
     * @protected
     * @param {HttpHeaders} [_requestHeaders]
     * @returns {HttpHeaders}
     * @memberof BackendService
     */
    protected wrapHttpSecureHeaders(requestHeaders?: HttpHeaders): HttpHeaders {
        let headers = new HttpHeaders()
            .set('Content-Type', 'application/json; charset=utf-8')
            .set('xApp-Name', environment.appName)
            .set('xApp-Version', environment.appVersion)
            .set('xApp-Platform', environment.platform)
            .set('xApp-Signature', 'S0000.0016.0001')
            .set('xApp-Signature', 'S0000.0019.0001');
        if (null != requestHeaders) { // Copy in additional headers.
            for (let key of requestHeaders.keys()) {
                headers = headers.set(key, requestHeaders.get(key));
            }
        }
        return headers;
    }
    private wrapHttpHandleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error.message);
        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong,
            console.error(
                `Backend returned code ${error.status}, ` +
                `Error message ${error.message}, `);

            // Do some generic error processing.
            // 401 are accesses without the token so we should move right to the login page.
            if (error.status == 401) {
                // this.router.navigate(['login']);
                return throwError('Autenticacion ya no valida. Es necesario logarse de nuevo.');
            }
        }
        // return an observable with a user-facing error message
        return throwError(
            'Something bad happened; please try again later.');
    }
}
