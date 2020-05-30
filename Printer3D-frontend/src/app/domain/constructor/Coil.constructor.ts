// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';

export class CoilConstructor implements Constructor<Coil> {
    public construct(input: any): Coil {
        if (null != input)
            return new Coil({
                id: input['id'],
                material: input['material'],
                color: input['color'],
                weight: input['weight'],
            })
    }
}
