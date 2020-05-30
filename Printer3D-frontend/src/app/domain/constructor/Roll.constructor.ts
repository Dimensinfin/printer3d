// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';

export class RollConstructor implements Constructor<Coil> {
    public construct(input: any): Coil {
        return new Coil({
            id: input['id'],
            material: input['material'],
            color: input['color'],
            weight: input['weight'],
        })
    }
}
