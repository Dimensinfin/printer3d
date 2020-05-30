// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { Subscription } from 'rxjs';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { CoilTransformer } from '@domain/transformer/CoilTransformer.transformer';
import { CoilRecord } from '@domain/CoilRecord.domain';

@Component({
    selector: 'inventory-coil-list-page',
    templateUrl: './inventory-coil-list-page.component.html',
    styleUrls: ['./inventory-coil-list-page.component.scss']
})
export class InventoryCoilListPageComponent implements OnInit, OnDestroy, Refreshable {
    public pagePath: string = '/Inventario/Lista Rollos';
    public columnDefs: GridColumn[] = [];
    public rowData: CoilRecord[] = [];
    private recordContainer: CoilTransformer = new CoilTransformer();
    private backendConnections: Subscription[] = [];

    constructor(protected backend: BackendService) { }

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
        this.backendConnections.push(
            this.backend.apiInventoryCoils_v1(new ResponseTransformer().setDescription('Transforms Inventory Coil list form backend.')
                .setTransformation((entrydata: any): CoilListResponse => {
                    return new CoilListResponse(entrydata);
                }))
                .subscribe((response: CoilListResponse) => {
                    // Convert DTO data into Grid data with a Converter
                    response.coils.forEach(record => {
                        this.recordContainer.addData(this.recordContainer.transform(record));
                    });
                    this.rowData = this.recordContainer.getRecords();
                })
        )
    }
}
