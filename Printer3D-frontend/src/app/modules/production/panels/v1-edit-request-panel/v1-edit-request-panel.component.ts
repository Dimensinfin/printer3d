// - CORE
import { Component } from '@angular/core'
// - ROUTER
import { Router } from '@angular/router'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { IsolationService } from '@app/platform/isolation.service'
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Part } from '@domain/inventory/Part.domain'
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component'
import { RequestFormToRequestConverter } from '@domain/converter/RequestFormToRequest.converter'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { RequestItem } from '@domain/production/RequestItem.domain'
import { Model } from '@domain/inventory/Model.domain'
import { DockService } from '@app/modules/innovative/feature-dock/service/dock.service'
import { RequestForm } from '../../domain/RequestForm.domain'
import { ProductionService } from '../../service/production.service'
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto'

@Component({
    selector: 'v1-edit-request-panel',
    templateUrl: './v1-edit-request-panel.component.html',
    styleUrls: ['./v1-edit-request-panel.component.scss']
})
export class V1EditRequestPanelComponent extends BackgroundEnabledComponent {
    public request: RequestForm = new RequestForm()

    constructor() { super() }

    public ngOnInit(): void {
    }
    public saveRequest() { }
    public isFormValid(form: boolean): boolean {
        return true
    }
}
