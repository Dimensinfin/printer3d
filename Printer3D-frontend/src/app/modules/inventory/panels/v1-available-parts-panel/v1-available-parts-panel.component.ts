// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { Part } from '@domain/inventory/Part.domain'

@Component({
    selector: 'v1-available-parts-panel',
    templateUrl: './v1-available-parts-panel.component.html',
    styleUrls: ['./v1-available-parts-panel.component.scss']
})
export class V1AvailablePartsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(protected backendService: BackendService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[V1AvailablePartsPanelComponent.ngOnInit]")
        super.ngOnInit()
        console.log("<[V1AvailablePartsPanelComponent.ngOnInit]")
    }

    // - R E F R E S H A B L E
    public clean(): void {
    }
    /**
     * When the page gets the list of Parts it should scan it and generate a list of Part Containers with distinct labels. Inside that containers there will be the Parts, each one with their different configurations.
     * Part containers will be ordered by their active status. Active parts will be listed before inactive groups.
     */
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
                    this.completeDowload(this.sortPartsByLabel(
                        this.filterActiveParts(response)
                    )) // Notify the completion of the download.
                })
        )
    }

    private filterActiveParts(parts: Part[]): Part[] {
        const activeParts = []
        for (const part of parts) {
            if (part.active) activeParts.push(part)
        }
        return activeParts
    }
    private sortPartsByLabel(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part2.label == part1.label)
                return this.orderByMaterial(part1, part2)
            return 0 - (part2.label > part1.label ? 1 : -1)
        })
    }
    private orderByMaterial(part1: Part, part2: Part): number {
        if (part1.material == part2.material)
            return this.orderByColor(part1, part2)
        else
            return 0 - (part2.material > part1.material ? 1 : -1)
    }
    private orderByColor(part1: Part, part2: Part): number {
        return 0 - (part2.color > part1.color ? 1 : -1)
    }
}
