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

const DEFAULT_PART_RECORD: string = '{    "label": "Covid - 19 Key - A",    "description": "Llavero para evitar tocar manillas y pulsadores durante la campaña de Covi - 19. Modelo A que es el más simple en un solo color y sin refuerzos.",    "buildTime": 60,    "affinity": "OFF",    "stockLevel": 2,   "colours": "ALL",    "active": true}';
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
            new GridColumn({ headerName: 'Label', field: 'label', sortable: true, checkboxSelection: true, width: 200 }),
            new GridColumn({ headerName: 'Description', field: 'description', sortable: true, width: 650 }),
            new GridColumn({ headerName: 'BuildTime', field: 'buildTime', sortable: true, filter: true, width: 150 }),
            // new GridColumn({ headerName: 'Affinity', field: 'affinity', sortable: false, width: 150 }),
            new GridColumn({ headerName: 'Stock Level', field: 'stockLevel', sortable: true, width: 150 }),
            new GridColumn({ headerName: 'Colours', field: 'colours', sortable: true, width: 100 }),
            new GridColumn({ headerName: 'Cost', field: 'cost', sortable: true, width: 120 }),
            new GridColumn({ headerName: 'PVP', field: 'pvp', sortable: true, width: 100 })
            // new GridColumn({ headerName: 'Active', field: 'active', sortable: true, width: 80 })
        ];
        // this.rowData.push(new PartRecord(DEFAULT_PART_RECORD));
        // Read row data from the assets mock data.
        this.backend.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
            .setTransformation((entrydata: any): PartListResponse => {
                return new PartListResponse(entrydata);
            }))
            .subscribe((response: PartListResponse) => {
                // Extract raw data to put it into the grid.
                this.rowData = response.records;
            });
    }
}
