// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

export class PartToPartRecordConverter implements Converter<Part, PartRecord>{
    public convert(input: Part): PartRecord {
        console.log('[PartToPartRecordConverter.convert]> Part: ' + JSON.stringify(input))
        const record = new PartRecord({
            etiqueta: input.label,
            descripcion: input.description,
            material: input.material,
            color: input.color,
            coste: this.convertToMoney(input.cost),
            precio: this.convertToMoney(input.price),
            tiempo: this.convertToBuildTime(input.buildTime),
            stockRequerido: input.stockLevel,
            stockDisponible: input.stockAvailable,
            active: this.convertToActive(input.active)
        })
        console.log('[PartToPartRecordConverter.convert]> Record: ' + JSON.stringify(record))
        return record;
    }
    private convertToMoney(input: number): string {
        return input + ' €';
    }
    private convertToActive(input: boolean): string {
        if (input) return 'ACTIVA';
        else return 'INACTIVA'
    }
    private convertToBuildTime(time: number): string {
        return time + ' min.';
    }
}
