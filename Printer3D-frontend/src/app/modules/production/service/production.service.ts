// - CORE
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'
import { HttpHeaders } from '@angular/common/http'
// - SERVICES
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
// - DOMAIN
import { Printer3DConstants } from "@app/platform/Printer3DConstants.platform"
import { BackendService } from "@app/services/backend.service"
import { Coil } from '@domain/inventory/Coil.domain'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto'
import { environment } from '@env/environment'
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto'
import { IsolationService } from '@app/platform/isolation.service'
import { CoilToUpdateCoilRequestConverter } from '@domain/converter/CoilToUpdateCoilRequest.converter'

@Injectable({
    providedIn: 'root'
})
export class ProductionService extends BackendService {
    private PRODUCTIONAPIV1: string

    constructor(
        protected httpService: HttpClientWrapperService,
        protected isolationService: IsolationService) {
        super(httpService)
        this.PRODUCTIONAPIV1 = Printer3DConstants.APIVERSION1
    }

}
