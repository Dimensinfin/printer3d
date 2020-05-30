// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { Subscription } from 'rxjs';
import { PartTransformer } from '@domain/transformer/PartTransformer.tranformer';
import { Part } from '@domain/Part.domain';

@Component({
    selector: 'inventory-part-list-page',
    templateUrl: './inventory-part-list-page.component.html',
    styleUrls: ['./inventory-part-list-page.component.scss']
})
export class InventoryPartListPageComponent implements OnInit, OnDestroy, Refreshable {
    public pagePath: string = '/Inventario/Lista Piezas';
    public columnDefs: GridColumn[] = [];
    public rowData: PartRecord[] = [];
     private recordContainer: PartTransformer = new PartTransformer();
    private backendConnections: Subscription[] = [];

    constructor(
        protected appStore: AppStoreService,
        protected backend: BackendService) { }

    public ngOnInit(): void {
        this.columnDefs = this.recordContainer.getDefinitions();
        this.refresh();
    }
    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
    }
    public refresh(): void {
        this.backend.apiInventoryParts_v1(new ResponseTransformer().setDescription('Transforms Inventory Part list form backend.')
            .setTransformation((entrydata: any): PartListResponse => {
                return new PartListResponse(entrydata);
            }))
            .subscribe((response: PartListResponse) => {
                // Convert DTO data into Grid data with a Converter
                response.parts.forEach(record => {
                    this.recordContainer.transform(record);
                });
                this.rowData = this.recordContainer.getRecords();
            });
    }
}
