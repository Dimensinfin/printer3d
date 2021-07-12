// - CORE
import { Component, OnInit } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { MachineV2 } from '@domain/production/MachineV2.domain'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component'

@Component({
    selector: 'v2-production-job-list-page',
    templateUrl: './v2-production-job-list-page.component.html',
    styleUrls: ['./v2-production-job-list-page.component.scss']
})
export class V2ProductionJobListPageComponent extends BackgroundEnabledComponent implements OnInit, Refreshable {
    public active: boolean = true
    public machines: MachineV2[] = []

    constructor(private backendService: BackendService) { super() }

    public ngOnInit(): void {
        this.refresh()
    }
    // - R E F R E S H A B L E
    public clean(): void {
        this.active = true
        this.machines = []
    }
    public refresh(): void {
        this.clean()
        this.getMachines()
    }

    private getMachines(): void {
        console.log('>[V2MachinesPanelComponent.getMachines]')
        this.backendConnections.push(
            this.backendService.apiInventoryGetMachines_v2(
                new ResponseTransformer().setDescription('Do HTTP transformation to "MachineV2" list.')
                    .setTransformation((entrydata: any): MachineV2[] => {
                        const recordList: MachineV2[] = []
                        for (let entry of entrydata)
                            recordList.push(new MachineV2(entry))
                        return recordList
                    }))
                .subscribe((response: MachineV2[]) => {
                    this.machines = this.sortMachinesByLabel(response)
                    this.machines=  this.machines.concat(this.sortMachinesByLabel(response))
                    this.machines=  this.machines.concat(this.sortMachinesByLabel(response))
                })
        )
        console.log('<[V2MachinesPanelComponent.getMachines]')
    }
    private sortMachinesByLabel(containers: MachineV2[]): MachineV2[] {
        return containers.sort((machine1, machine2) =>
            0 - (machine2.label > machine1.label ? 1 : -1)
        )
    }
}
