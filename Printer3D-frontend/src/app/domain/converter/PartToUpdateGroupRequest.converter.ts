// - DOMAIN
import { Part } from '@domain/inventory/Part.domain';
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto';
import { Converter } from '@domain/interfaces/Converter.interface';

export class PartToUpdateGroupRequestConverter implements Converter<Part, UpdateGroupRequest> {
    public convert(input: Part): UpdateGroupRequest {
        return new UpdateGroupRequest({
            label: input.label,
            description: input.description,
            weight: input.weight,
            buildTime: input.buildTime,
            imagePath: input.imagePath,
            modelPath: input.modelPath
        })
    }
}
