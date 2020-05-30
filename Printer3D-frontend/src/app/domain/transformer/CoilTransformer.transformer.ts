// - DOMAIN
import { GridColumn } from '@domain/GridColumn.domain';
import { PartRecord } from '@domain/PartRecord.domain';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { CoilToCoilRecordConverter } from '@domain/converter/CoilToCoilRecord.converter';
import { Transformer } from '../interfaces/Transformer.interface';

export class CoilTransformer implements Transformer<Coil, CoilRecord>{
    private columnDefinitions: GridColumn[] = [];
    private rowData: CoilRecord[] = [];

    constructor() {
        this.columnDefinitions.push(new GridColumn({ headerName: 'Material', field: 'material', sortable: true, width: 200 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Color', field: 'color', sortable: true, width: 200 }));
        this.columnDefinitions.push(new GridColumn({ headerName: 'Peso', field: 'peso', sortable: true, width: 200 }));
    }
    public getDefinitions(): GridColumn[] {
        return this.columnDefinitions;
    }
    public getRecords(): CoilRecord[] {
        return this.rowData;
    }
    public clear(): CoilTransformer {
        this.rowData = [];
        return this;
    }
    public recordCount(): number {
        return this.rowData.length;
    }
    public addData(newRecord: CoilRecord): void {
        this.rowData.push(newRecord);
    }
    public transform(input: Coil): CoilRecord {
        return new CoilToCoilRecordConverter().convert(input);
    }
}
