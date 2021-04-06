// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/inventory/Part.domain';
import { Model } from '@domain/inventory/Model.domain';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { PartContainer } from '@domain/inventory/PartContainer.domain';
import { V3InventoryPageComponent } from '../../pages/v3-inventory-page/v3-inventory-page.component';
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';

@Component({
    selector: 'v1-catalog-panel',
    templateUrl: './v1-catalog-panel.component.html',
    styleUrls: ['./v1-catalog-panel.component.scss']
})
export class V1CatalogPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IContentProvider {
    @Input() page: V3InventoryPageComponent  // Pointer to the page that contains this panel. Uset as a way to connect to siblings
    public filterInactive: boolean = true;
    private parts: Part[] = []
    private models: Model[] = []
    private partContainers: Map<string, PartContainer> = new Map<string, PartContainer>()
    private items: ICollaboration[] = []

    constructor(protected backendService: BackendService) {
        super();
    }

    public ngOnInit(): void {
        console.log(">[V3InventoryPageComponent.ngOnInit]");
        super.ngOnInit()
        console.log("<[V3InventoryPageComponent.ngOnInit]");
    }
    // - I N T E R A C T I O N
    public toggleFilter(): void {
        console.log("-[V1CatalogPanelComponent.changeFilter]")
        this.refresh()
    }

    // - I C O N T E N T P R O V I D E R
    public findById(id: string, type: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part;
        return undefined;
    }

    // - I V I E W E R
    /**
     * This method is called whenever the selection changes. Used to spread the event or perform other actions.
     */
    public fireSelectionChanged(): void {
        if (null != this.page)
            if (this.selection.getFirstSelected() instanceof Model)
                this.page.setSelected(this.selection.getFirstSelected() as Model)
            else this.page.closeEditor()
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.selection.clearSelection() // Clear the selection
        this.parts = []
        this.models = []
        this.partContainers = new Map<string, PartContainer>()
        this.items = []
    }
    public refresh(): void {
        this.clean()
        this.downloadParts()
    }

    // - B A C K E N D
    protected downloadParts(): void {
        console.log("-[V1CatalogPanelComponent.downloadParts]>Filter: " + this.filterInactive)
         this.backendConnections.push(
            this.backendService.apiv2_InventoryGetParts()
                .subscribe((response: Part[]) => {
                    console.log('downloadParts.part count: ' + response.length)
                    this.parts = response
                    // Sort the Parts before storing them inside the containers.
                    const partList = this.sortPartsByActive(response)
                    // Classify Parts on part containers.
                    partList.forEach(element => {
                        let hit = this.partContainers.get(element.label)
                        if (null == hit) {
                            hit = new PartContainer(element)
                            this.partContainers.set(element.label, hit)
                        }
                        if (this.filterInactive) {  // Filter out inactive items
                            if (element.isActive()) hit.addPart(element)
                        } else hit.addPart(element)
                    })
                    this.partContainers = this.removeEmptyContainers()
                    this.downloadModels()
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(this)
                .subscribe((response: Model[]) => {
                    this.models = response
                    // Join the list of Parts and the list of Models in order
                    this.items = []
                    for (const item of this.models) {
                        if (this.filterInactive) {// Filter out inactive items
                            if (item.isActive()) this.items.push(item)
                        } else this.items.push(item)
                    }
                    // Load the containers on the root for the MVC.
                    const containers = this.partContainers.values();
                    const sortedContainers: PartContainer[] = []
                    for (const container of containers)
                        sortedContainers.push(container)
                    for (const container of this.sortPartContainersByLabel(sortedContainers)) {
                        this.items.push(container)
                    }
                    this.completeDowload(this.items)
                })
        )
    }
    private removeEmptyContainers(): Map<string, PartContainer> {
        const filteredContainers: Map<string, PartContainer> = new Map<string, PartContainer>()
        this.partContainers.forEach((value, key) => {
            if (!this.isEmpty(value.contents))
                filteredContainers.set(key, value)
        })
        return filteredContainers
    }
    private sortPartsByActive(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part1.active && !part2.active) return -1
            if (!part1.active && part2.active) return 1
            return 0
        })
    }
    private sortPartContainersByLabel(containers: PartContainer[]): PartContainer[] {
        return containers.sort((container1, container2) =>
            0 - (container2.label > container1.label ? 1 : -1)
        )
    }
}
