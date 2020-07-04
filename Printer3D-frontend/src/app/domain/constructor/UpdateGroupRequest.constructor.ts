// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Request } from '@domain/Request.domain';
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface';
import { IContent } from '@domain/interfaces/IContent.interface';
import { RequestItem } from '@domain/RequestItem.domain';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { UpdateGroupRequest } from '@domain/dto/UpdateGroupRequest.dto';

export class UpdateGroupRequestConstructor implements Constructor<UpdateGroupRequest> {
    public construct(input: any): UpdateGroupRequest {
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
