// - CORE
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'
import { HttpHeaders } from '@angular/common/http'
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { BackendService } from "@app/services/backend.service"
import { IsolationService } from '@app/platform/isolation.service'
// - DOMAIN
import { Printer3DConstants } from "@app/platform/Printer3DConstants.platform"
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { CustomerRequestResponse } from '../domain/dto/CustomerRequestResponse.dto'
import { RequestForm } from '../domain/RequestForm.domain'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'
import { DataToRequestConverter } from '../converter/DataToRequest.converter'

@Injectable({
    providedIn: 'root'
})
export class ProductionService extends BackendService {
    private PRODUCTIONAPIV1: string
    private PRODUCTIONAPIV2: string
    private PRODUCTIONAPIV3: string

    constructor(
        protected httpService: HttpClientWrapperService,
        protected isolationService: IsolationService) {
        super(httpService)
        this.PRODUCTIONAPIV1 = Printer3DConstants.APIVERSION1 + '/production'
        this.PRODUCTIONAPIV2 = Printer3DConstants.APIVERSION2 + '/production'
        this.PRODUCTIONAPIV3 = Printer3DConstants.APIVERSION3 + '/production'
    }
    /**
     * Persists a new Customer Request into the backend repository. Accepts a UI request form that is converted to the backend request api structure if required. This Production services is compliant with backend version 0.17 and others.
     * @param newRequestForm the request form used on the UI to collect request data from the user.
     * @returns the Customer Request Response as returned by the backend service with the full Request data structure and more dependent fields completed.
     */
    public apiv2_ProductionNewRequest(newRequestForm: RequestForm): Observable<CustomerRequestResponse> {
        const request = this.PRODUCTIONAPIV2 + '/requests'
        const transformer: ResponseTransformer = new ResponseTransformer()
            .setDescription('Do HTTP transformation to "CustomerRequestResponse" dto instance from response.')
            .setTransformation((entrydata: any): CustomerRequestResponse => {
                this.isolationService.successNotification('Pedido [' + newRequestForm.label + '] registrado correctamente.', '/PRODUCCION/NUEVO PEDIDO/OK')
                return new CustomerRequestResponse() // Discard the just persisted request and return an empty instance.
            })
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newRequestForm))
            .pipe(map((data: any) => {
                console.log(">[ProductionService.apiv2_NewRequest]> Transformation: " + transformer.description)
                return transformer.transform(data) as CustomerRequestResponse
            }))
    }
    public apiv3_ProductionGetOpenRequests(provider: IContentProvider): Observable<CustomerRequestResponse[]> {
        const request = this.PRODUCTIONAPIV3 + '/requests/open'
        const transformer = new ResponseTransformer()
            .setDescription('Transforms response into a list of "CustomerRequests".')
            .setTransformation((entrydata: any): CustomerRequestResponse[] => {
                console.log('-[ProductionService.apiv2_ProductionGetOpenRequests]>Processing Requests')
                // Extract requests from the response and convert them to the CustomerRequest V2 format. Resolve contents id references.
                const requestList: CustomerRequestResponse[] = []
                const requestConverter: DataToRequestConverter = new DataToRequestConverter(provider)
                for (let index = 0; index <entrydata.length; index++) {
                    requestList.push(requestConverter.convert(entrydata[index]))
                }
                return requestList
            })
        let headers = new HttpHeaders()
        headers = headers.set('xApp-Api-Version', 'API v2')
        return this.httpService.wrapHttpGETCall(request, headers)
            .pipe(map((data: any) => {
                console.log(">[ProductionService.apiv2_ProductionGetOpenRequests]> Transformation: " + transformer.description)
                return transformer.transform(data) as CustomerRequestResponse[]
            }))
    }
    public apiv3_ProductionRequestsDeliver(requestId: string): Observable<CustomerRequestResponse> {
        const request = this.PRODUCTIONAPIV3 + '/requests/' + requestId + '/deliver'
        const transformer: ResponseTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
            .setTransformation((entrydata: any): CustomerRequestResponse => {
                return new CustomerRequestResponse(entrydata)
            })
        return this.httpService.wrapHttpPUTCall(request)
            .pipe(map((data: any) => {
                console.log(">[ProductionService.apiv3_ProductionRequestsDeliver]> Transformation: " + transformer.description)
                return transformer.transform(data) as CustomerRequestResponse
            }))
    }
    public apiv3_ProductionDeleteRequest(requestId: string): Observable<CustomerRequestResponse> {
        const request = this.PRODUCTIONAPIV3 + '/requests/' + requestId
        const transformer: ResponseTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
            .setTransformation((entrydata: any): CustomerRequestResponse => {
                return new CustomerRequestResponse(entrydata)
            })
        return this.httpService.wrapHttpDELETECall(request)
            .pipe(map((data: any) => {
                console.log(">[ProductionService.apiv3_ProductionDeleteRequest]> Transformation: " + transformer.description)
                return transformer.transform(data)
            }))
    }
}
