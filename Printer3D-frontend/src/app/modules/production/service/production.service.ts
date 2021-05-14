// - CORE
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
// - DOMAIN
import { Printer3DConstants } from "@app/platform/Printer3DConstants.platform"
import { BackendService } from "@app/services/backend.service"
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { IsolationService } from '@app/platform/isolation.service'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { CustomerRequestResponse } from '../domain/dto/CustomerRequestResponse.dto'
import { RequestForm } from '../domain/RequestForm.domain'

@Injectable({
    providedIn: 'root'
})
export class ProductionService extends BackendService {
    private PRODUCTIONAPIV1: string
    private PRODUCTIONAPIV2: string

    constructor(
        protected httpService: HttpClientWrapperService,
        protected isolationService: IsolationService) {
        super(httpService)
        this.PRODUCTIONAPIV1 = Printer3DConstants.APIVERSION1
        this.PRODUCTIONAPIV2 = Printer3DConstants.APIVERSION2 + '/production'
    }
    /**
     * Persists a new Customer Request into the backend repository. Accepts a UI request form that is converted to the backend request api structure if required. This Production services is compliant with backend version 0.17 and others.
     * @param newRequestForm the request form used on the UI to collect request data from the user.
     * @returns the Customer Request Response as returned by the backend service with the full Request data structure and more dependent fields completed.
     */
    public apiv2_NewRequest(newRequestForm: RequestForm): Observable<CustomerRequestResponse> {
        const request = this.PRODUCTIONAPIV2 + '/requests'
        const transformer: ResponseTransformer = new ResponseTransformer()
            .setDescription('Do HTTP transformation to "CustomerRequestResponse" dto instance from response.')
            .setTransformation((entrydata: any): CustomerRequestResponse => {
                this.isolationService.successNotification('Pedido [' + newRequestForm.label + '] registrado correctamente.', '/PRODUCCION/NUEVO PEDIDO/OK')
                return new CustomerRequest() // Discard the just persisted request and return an empty instance.
            })
        return this.httpService.wrapHttpPOSTCall(request, JSON.stringify(newRequestForm))
            .pipe(map((data: any) => {
                console.log(">[ProductionService.apiv2_NewRequest]> Transformation: " + transformer.description)
                return transformer.transform(data) as CustomerRequestResponse
            }))
    }
}
