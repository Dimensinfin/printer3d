// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { PartListResponse } from '@domain/dto/PartListResponse.dto'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'

@Component({
    selector: 'v1-requestable-elements-panel',
    templateUrl: './v1-requestable-elements-panel.component.html',
    styleUrls: ['./v1-requestable-elements-panel.component.scss']
})
export class V1RequestableElementsPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IContentProvider {
    private parts: Part[] = []
    private models: Model[] = []
    private items: ICollaboration[] = []

    constructor(protected backendService: BackendService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[V1OpenRequestsPanelComponent.ngOnInit]")
        super.ngOnInit()
        console.log("<[V1OpenRequestsPanelComponent.ngOnInit]")
    }

    // - I C O N T E N T P R O V I D E R
    public findById(id: string, type: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part
        return undefined
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.parts = []
        this.models = []
        this.items = []
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
    }

    // - B A C K E N D
    protected downloadParts(): void {
        this.backendConnections.push(
            this.backendService.apiv2_InventoryGetParts()
                .subscribe((response: Part[]) => {
                    console.log('downloadParts.part count: ' + response.length)
                    // Filter out unavailable Parts.
                    for (let part of response)
                        if (!part.unavailable) this.parts.push(part)
                    console.log('downloadParts.part count after filter: ' + this.parts.length)
                    this.downloadModels()
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(this)
                .subscribe((response: Model[]) => {
                    this.models = response
                    this.generateItems()
                })
        )
    }
    protected generateItems(): void {
        // Join the list of Parts and the list of Models in order
        this.items = []
        console.log('models: ' + this.models.length)
        for (const item of this.models) {
            // console.log('model: ' + JSON.stringify(item))
            // console.log('model is Model: ' +(item instanceof Model))
            // console.log('model is IContent: ' + (item instanceof IContent))
            if (item.isActive()) this.items.push(item)
        }
        console.log('parts: ' + this.parts.length)
        for (const item of this.parts) {
            // console.log('part: ' + JSON.stringify(item))
            if (item.isActive()) this.items.push(item)
        }
        this.completeDowload(this.items)
    }
}
