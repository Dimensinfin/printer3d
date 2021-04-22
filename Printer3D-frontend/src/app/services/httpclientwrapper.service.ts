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
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform';

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
    public wrapHttpPUTCall(request: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpPUTCall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.put(request, { headers: newheaders });
    }
    public wrapHttpDELETECall(request: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpDELETECall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.delete(request, { headers: newheaders });
    }
    public wrapHttpPATCHCall(request: string, body: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpPATCHCall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.patch(request, body, { headers: newheaders });
    }
    public wrapHttpPOSTCall(request: string, body: string, requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpPOSTCall]> request: " + request);
        let newheaders = this.wrapHttpSecureHeaders(requestHeaders);
        return this.http.post(request, body, { headers: newheaders })
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
        headers = headers.set('Content-Type', 'application/json; charset=utf-8')
        headers = headers.set('xApp-Name', environment.appName)
        headers = headers.set('xApp-Version', environment.appVersion)
        headers = headers.set('xApp-Platform', Printer3DConstants.PLATFORM)
        headers = headers.set('xApp-Signature', Printer3DConstants.APPSIGNATURE)
        headers = headers.set('xApp-Api-Version', 'API v1');
        if (null != requestHeaders) { // Copy in additional headers.
            for (let key of requestHeaders.keys()) {
                headers = headers.set(key, requestHeaders.get(key));
            }
        }
        return headers;
    }
}
