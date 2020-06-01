// - DOMAIN
import { Job } from '@domain/Job.domain';

export class PendingJobListResponse {
    public count: number = 0;
    public jobs: Job[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }

    public getJobs(): any[] {
        return this.jobs;
    }
    private transformInput(): void {
        if (null != this.jobs) {
            const recordList: Job[] = [];
            for (let entry of this.jobs)
                recordList.push(new Job(entry));
            this.jobs = recordList;
        }
    }

}
