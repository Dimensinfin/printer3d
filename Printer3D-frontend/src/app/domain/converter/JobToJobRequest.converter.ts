// - DOMAIN
import { JobRequest } from '@domain/dto/JobRequest.dto';
import { Job } from '@domain/production/Job.domain';
import { Converter } from '@domain/interfaces/Converter.interface';

export class JobToJobRequestConverter implements Converter<Job,JobRequest> {
   public convert(input: Job): JobRequest {
        return new JobRequest ({
            id: input.getId(),
            partId: input.getPart().getId(),
            copies: input.getCopies()
        })
    }
}
