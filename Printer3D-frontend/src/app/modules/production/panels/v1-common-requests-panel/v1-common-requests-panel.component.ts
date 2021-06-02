// - CORE
import { Component, OnInit } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { IContent } from '@domain/interfaces/IContent.interface'
import { RequestContentType } from '@domain/interfaces/EPack.enumerated'

@Component({
    selector: 'v1-common-requests-panel',
    templateUrl: './empty.html'
})
export class V1CommonRequestsPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IContentProvider {
    protected parts: Part[] = []
    protected models: Model[] = []

    constructor(protected backendService: BackendService) {
        super()
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
    /**
     * This method should be overriden by child components to select the correct list of Requests. The y can select from the OPen or the Closed requests using different backend entry points.
     */
    protected downloadRequests(): void {
    }

    private sortPartsByActive(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part1.active && !part2.active) return -1
            if (!part1.active && part2.active) return 1
            return 0
        })
    }
}
