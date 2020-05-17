// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/part-list-response.domain';

@Component({
    selector: 'inventory-part-list-page',
    templateUrl: './inventory-part-list-page.component.html',
    styleUrls: ['./inventory-part-list-page.component.scss']
})
export class InventoryPartListPageComponent implements OnInit {
    public pagePath: string = '/Inventory/Part List';
    public columnDefs: GridColumn[] = [];
    public rowData: PartRecord[] = [];
    constructor(protected appStore: AppStoreService,
        protected backend: BackendService) { }

    public ngOnInit(): void {
        this.columnDefs = [
            new GridColumn({ headerName: 'label', field: 'label', sortable: true, checkboxSelection: true }),
            new GridColumn({ headerName: 'description', field: 'description', sortable: true }),
            new GridColumn({ headerName: 'buildTime', field: 'buildTime', sortable: true, filter: true }),
            new GridColumn({ headerName: 'affinity', field: 'affinity', sortable: false }),
            new GridColumn({ headerName: 'stockLevel', field: 'stockLevel', sortable: true }),
            new GridColumn({ headerName: 'colours', field: 'colours', sortable: true }),
            new GridColumn({ headerName: 'active', field: 'active', sortable: true })
        ];
        // Read row data from the assets mock data.
        this.backend.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforma Inventory Part list form backend.')
            .setTransformation((entrydata: any): PartListResponse => {
                return new PartListResponse(entrydata);
            }))
            .subscribe((response:PartListResponse) => {
                // Extract raw data to put it into the grid.
                this.rowData = response.records;
            });
    }
}
