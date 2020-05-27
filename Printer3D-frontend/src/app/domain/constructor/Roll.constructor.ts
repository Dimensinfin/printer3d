// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Roll } from '@domain/Roll.domain';

export class RollConstructor implements Constructor<Roll> {
    public construct(input: any): Roll {
        return new Roll({
            id: input['id'],
            material: input['material'],
            color: input['color'],
            weight: input['weight'],
        })
    }
}
