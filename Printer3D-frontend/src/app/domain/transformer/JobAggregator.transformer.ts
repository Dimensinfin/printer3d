// - DOMAIN
import { Job } from '@domain/Job.domain';

/**
 * Accepts one job and stores it on a reduced list of jobs. If the job has the same priority and the same Part than a job already on the list then the count for the found Job is incremented. If not found then the Job is added to the list.
 */
export class JobAggregator {
    private reducedJobs: Job[] = []

    public addJob(newJob: Job): void {
        for (let target of this.reducedJobs) {
            if (target.getPriority() == newJob.getPriority())
                if (target.getPart().getId() == newJob.getPart().getId()) {
                    target.aggregate()
                    return
                }
        }
        this.reducedJobs.push(newJob)
    }
    public getAggreagtedJobs(): Job[] {
        return this.reducedJobs
    }
}
