// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';

export class CoilToCoilRecordConverter implements Converter<Coil, CoilRecord> {
    public convert(input: Coil): CoilRecord {
        console.log('[CoilToCoilRecordConverter.convert]> Coil: ' + JSON.stringify(input))
        const record = new CoilRecord({
            material: input.material,
            color: input.color,
            peso: this.convertWeight(input.weight)
        })
        console.log('[PartToPartRecordConverter.convert]> Record: ' + JSON.stringify(record))
        return record;
    }
    private convertWeight(input: number): string {
        return input + ' gr';
    }
}
