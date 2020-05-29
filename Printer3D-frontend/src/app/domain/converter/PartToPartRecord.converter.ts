// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class PartToPartRecordConverter implements Converter<Part, PartRecord>{
    public convert(input: Part): PartRecord {
        return new PartRecord({
            etiqueta: input.label,
            descripcion: input.description,
            material: input.material,
            color: input.colorCode,
            coste: this.convertToMoney(input.cost),
            precio: this.convertToMoney(input.price)
        })
    }
    private convertToMoney(input: number): string {
        return input + ' €';
    }
}
