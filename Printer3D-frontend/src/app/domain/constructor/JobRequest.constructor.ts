// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { Coil } from '@domain/Coil.domain';
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { Job } from '@domain/Job.domain';

export class JobRequestConstructor implements Constructor<JobRequest> {
   public construct(input: Job): JobRequest {
        return new JobRequest ({
            id: input.getId(),
            partId: input.getPart().getId(),
            copies: input.getCopies
        })
    }
}
