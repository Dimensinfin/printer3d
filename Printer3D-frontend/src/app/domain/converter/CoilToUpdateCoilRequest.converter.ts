// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { UpdateCoilRequest } from '@domain/dto/UpdateCoilRequest.dto';

export class CoilToUpdateCoilRequestConverter implements Converter<Coil, UpdateCoilRequest>  {
    public convert(input: Coil): UpdateCoilRequest {
        return new UpdateCoilRequest({
            id: input.id,
            weight: input.weight
        })
    }
}
