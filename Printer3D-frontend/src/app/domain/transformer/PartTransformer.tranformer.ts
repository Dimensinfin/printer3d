// - DOMAIN
import { GridColumn } from '@domain/GridColumn.domain';
import { PartRecord } from '@domain/PartRecord.domain';
import { Part } from '@domain/Part.domain';
import { PartToPartRecordConverter } from '@domain/converter/PartToPartRecord.converter';

export class PartTransformer {
    private columnDefinitions: GridColumn[] = [];
    private rowData: PartRecord[] = [];

    constructor() {
        this.columnDefinitions.push(new GridColumn({ headerName: 'Etiqueta', field: 'etiqueta', sortable: true, width: 200 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Descripción', field: 'descripcion', sortable: true, width: 610 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Material', field: 'material', sortable: true, width: 100 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Color', field: 'color', sortable: true, width: 100 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Coste', field: 'coste', sortable: true, width: 100 }))
        this.columnDefinitions.push(new GridColumn({ headerName: 'PVP', field: 'precio', sortable: true, width: 90 }))
        this.columnDefinitions.push(new GridColumn({ headerName: 'Stock Rq.', field: 'stockRequerido', sortable: true, width: 120 }))
        this.columnDefinitions.push(new GridColumn({ headerName: 'Stock Disp.', field: 'stockDisponible', sortable: true, width: 120 }))
        this.columnDefinitions.push(new GridColumn({ headerName: 'Activa', field: 'active', sortable: true, width: 80 }))
        const data = [
            new GridColumn({ headerName: 'Description', field: 'description', sortable: true, width: 650 }),
            new GridColumn({ headerName: 'BuildTime', field: 'buildTime', sortable: true, filter: true, width: 150 }),
            // new GridColumn({ headerName: 'Affinity', field: 'affinity', sortable: false, width: 150 }),
            new GridColumn({ headerName: 'Colours', field: 'colours', sortable: true, width: 100 }),
        ];
    }
    public getDefinitions(): GridColumn[] {
        return this.columnDefinitions;
    }
    public getRecords(): PartRecord[] {
        return this.rowData;
    }
    public clear(): PartTransformer {
        this.rowData = [];
        return this;
    }
    public recordCount(): number {
        return this.rowData.length;
    }
    public transform(part: Part): void {
        this.rowData.push(new PartToPartRecordConverter().convert(part));
    }
}
