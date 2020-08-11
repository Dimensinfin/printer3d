// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Machine } from '@domain/production/Machine.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';

@Component({
    selector: 'v2-machines-panel',
    templateUrl: './v2-machines-panel.component.html',
    styleUrls: ['./v2-machines-panel.component.scss']
})
export class V2MachinesPanelComponent extends BackgroundEnabledComponent implements OnInit, Refreshable {
    public active: boolean = true;
    public machines: Machine[] = [];

    constructor(private backendService: BackendService) { super() }

    public ngOnInit(): void {
        this.refresh()
    }
    private getMachines(): void {
        console.log('>[getMachines]')
        this.backendConnections.push(
            this.backendService.apiInventoryGetMachines_v2(
                new ResponseTransformer().setDescription('Do HTTP transformation to "MachineListResponse".')
                    .setTransformation((entrydata: any): Machine[] => {
                        const recordList: Machine[] = [];
                        for (let entry of entrydata)
                            recordList.push(new Machine(entry));
                        return recordList;
                    }))
                .subscribe((response: Machine[]) => {
                    // Process the response to extract the Machines to the render list
                    this.machines = this.sortMachinesByLabel(response);
                })
        );
    }
    // - R E F R E S H A B L E
    public clean(): void {
        this.active = true;
        this.machines = [];
    }
    public refresh(): void {
        this.clean();
        this.getMachines();
    }
    private sortMachinesByLabel(containers: Machine[]): Machine[] {
        return containers.sort((machine1, machine2) =>
            0 - (machine2.label > machine1.label ? 1 : -1)
        )
    }

}
