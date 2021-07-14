// - CORE
import { Component, OnInit, Input } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { Part } from '@domain/inventory/Part.domain'
import { CustomerRequest } from '@domain/production/CustomerRequest.domain'
import { V1OpenRequestsPageComponent } from '../../pages/v1-open-requests-page/v1-open-requests-page.component'
import { Model } from '@domain/inventory/Model.domain'
import { RequestContentType } from '@domain/interfaces/EPack.enumerated'
import { IContent } from '@domain/interfaces/IContent.interface'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'
import { ProductionService } from '../../service/production.service'
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto'
/**
 * To display some of the Request details we should have access to the list of Parts because the Request List from the backend will not have the Part details but only the Part identifier.
 * The Process should lookup on the Parts list for the Part instance.
 */
@Component({
    selector: 'v1-open-requests-panel',
    templateUrl: './v1-open-requests-panel.component.html',
    styleUrls: ['./v1-open-requests-panel.component.scss']
})
export class V1OpenRequestsPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IContentProvider {
    @Input() page: V1OpenRequestsPageComponent
    private parts: Part[] = []
    private models: Model[] = []

    constructor(
        protected productionService: ProductionService,
        protected backendService: BackendService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[V1OpenRequestsPanelComponent.ngOnInit]")
        super.ngOnInit()
        console.log("<[V1OpenRequestsPanelComponent.ngOnInit]")
    }

    // - I V I E W E R
    public fireSelectionChanged(): void {
        this.page.selectRequest(this.selection.getFirstSelected() as CustomerRequest)
    }
    // - E V E N T S
    public selectRequest(request: CustomerRequest): void {
        this.page.selectRequest(request)
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.parts = []
        this.models = []
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
    }
    // - I C O N T E N T P R O V I D E R
    /**
     * Now identifier can belong to Parts or Models, the second parameter determined the type for the search.
     * @param id The item identifier to search
     */
    public findById(id: string, type: string): IContent {
        if (type == RequestContentType.PART)
            for (let part of this.parts)
                if (part.getId() == id) return part
        if (type == RequestContentType.MODEL)
            for (let model of this.models)
                if (model.getId() == id) return model
        return undefined
    }

    // - B A C K E N D
    protected downloadParts(): void {
        this.backendConnections.push(
            this.backendService.apiv2_InventoryGetParts()
                .subscribe((response: Part[]) => {
                    console.log('downloadParts.part count: ' + response.length)
                    this.parts = response
                    // Sort the Parts before storing them inside the containers.
                    const partList = this.sortPartsByActive(response)
                    this.downloadModels()
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(this)
                .subscribe((response: Model[]) => {
                    this.models = response
                    this.downloadRequests()
                })
        )
    }
    protected downloadRequests(): void {
        console.log('step.05')
        this.backendConnections.push(
            this.productionService.apiv3_ProductionGetOpenRequests(this)
                .subscribe((requestList: CustomerRequestResponse[]) => {
                    console.log('-[V1OpenRequestsPanelComponent.downloadRequests]>Requests received: ' + requestList.length)
                    this.completeDowload(requestList) // Notify the completion of the download.
                })
        )
    }
    private sortPartsByActive(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part1.active && !part2.active) return -1
            if (!part1.active && part2.active) return 1
            return 0
        })
    }
}
