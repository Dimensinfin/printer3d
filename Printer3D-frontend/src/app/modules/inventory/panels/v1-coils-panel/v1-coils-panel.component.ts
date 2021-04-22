// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
import { OnDestroy } from '@angular/core'
import { Input } from '@angular/core'
import { Subscription } from 'rxjs'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto'
import { Coil } from '@domain/inventory/Coil.domain'
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { environment } from '@env/environment'
import { HttpErrorResponse } from '@angular/common/http'
import { IsolationService } from '@app/platform/isolation.service'
import { InventoryService } from '../../service/inventory.service'
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface'
import { IFiltered } from '@domain/interfaces/IFiltered.interface'

@Component({
    selector: 'v1-coils-panel',
    templateUrl: './v1-coils-panel.component.html',
    styleUrls: ['./v1-coils-panel.component.scss']
})
export class V1CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
    public filter: string = ''
    public filterInactive: boolean = true;
    private coilList: ICollaboration[] = []

    constructor(
        protected isolationService: IsolationService,
        protected inventoryService: InventoryService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[V1CoilsPanelComponent.ngOnInit]")
        this.setVariant(EVariant.COIL_LIST)
        super.ngOnInit()
        console.log("<[V1CoilsPanelComponent.ngOnInit]")
    }

    // - I N T E R A C T I O N
    public changeFilter(): void {
        console.log("-[V1CatalogPanelComponent.changeFilter]")
        this.refresh()
    }
    public nodeCounter(): number {
        return this.coilList.length
    }

    /**
     * The panel implementation accepts a filter ans allow to remove some elements from rendering.
     * @returns The filtered list of nodes to render
     */
    public getNodes2Render(filter?: string): ICollaboration[] {
        if (filter) {
            const filteredList: ICollaboration[] = []
            for (const node of this.renderNodeList) {
                if (node['getRepresentation']) {
                    const representativeNode = node as IFiltered
                    // console.log(representativeNode.getRepresentation())
                    if (representativeNode.getRepresentation().toUpperCase().includes(filter.toUpperCase()))
                        filteredList.push(node)
                } else filteredList.push(node)
            }
            const size = filteredList.length
            console.log('size: ' + size)
            this.coilList = filteredList
        } else this.coilList = this.renderNodeList
        return this.coilList
    }

    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.downloadCoils()
    }
    // - B A C K E N D
    public downloadCoils(): void {
        console.log(">[V1CoilsPanelComponent.downloadCoils]")
        this.backendConnections.push(
            this.inventoryService.apiv2_InventoryGetCoils()
                .subscribe((coilList: Coil[]) => {
                    console.log('-[V1CoilsPanelComponent.downloadCoils]> Nodes downloaded: ' + coilList.length)
                    this.coilList=this.sortCoilByIdentificationFields(
                        this.sortCoilByWeightDesc(
                            this.filterActiveCoils(coilList))
                    ) // Notify the completion of the download.
                    this.completeDowload(this.coilList)
                })
        )
        console.log("<[V1CoilsPanelComponent.downloadCoils]")
    }
    private filterActiveCoils(sourceList: Coil[]): Coil[] {
        if (this.filterInactive) {
            const activeCoils = []
            for (const coil of sourceList) {
                if (coil.active) activeCoils.push(coil)
            }
            return activeCoils
        }
        else return sourceList
    }
    private sortCoilByWeightDesc(containers: Coil[]): Coil[] {
        return containers.sort((coil1, coil2) =>
            0 - (coil2.weight > coil1.weight ? 1 : -1)
        )
    }
    private sortCoilByIdentificationFields(containers: Coil[]): Coil[] {
        return containers.sort((coil1, coil2) =>
            0 - (coil2.tradeMark + coil2.material + coil2.color > coil1.tradeMark + coil1.material + coil1.color ? 1 : -1)
        )
    }
}
