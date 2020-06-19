// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';

@Component({
    selector: 'v2-machines-panel',
    templateUrl: './v2-machines-panel.component.html',
    styleUrls: ['./v2-machines-panel.component.scss']
})
export class V2MachinesPanelComponent implements OnInit, OnDestroy, Refreshable {
    private backendConnections: Subscription[] = [];
    public active: boolean = true;
    public machines: Machine[] = [];

    constructor(private backendService: BackendService) { }

    public ngOnInit(): void {
        this.getMachines();
    }
    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
    }
    private getMachines(): void {
        console.log('>[getMachines]')
        this.backendConnections.push(
            this.backendService.apiInventoryGetMachines_v2(
                new ResponseTransformer().setDescription('Do HTTP transformation to "MachineListResponse".')
                    .setTransformation((entrydata: any): MachineListResponse => {
                        return new MachineListResponse(entrydata);
                    }))
                .subscribe((response: MachineListResponse) => {
                    // Process the response to extract the Machines to the render list
                    this.machines = response.getMachines();
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
}
