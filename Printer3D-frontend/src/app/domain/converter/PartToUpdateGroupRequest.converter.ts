// - DOMAIN
import { Part } from '@domain/inventory/Part.domain';
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto';
import { Converter } from '@domain/interfaces/Converter.interface';
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform';

export class PartToUpdateGroupRequestConverter implements Converter<Part, UpdateGroupRequest> {
    public convert(input: Part): UpdateGroupRequest {
        let projectName: string
        if (input.project) projectName = input.project
        else projectName = Printer3DConstants.DEFAULT_PROJECT_NAME
        return new UpdateGroupRequest({
            label: input.label,
            project: projectName,
            description: input.description,
            weight: input.weight,
            buildTime: input.buildTime,
            imagePath: input.imagePath,
            modelPath: input.modelPath
        })
    }
}
