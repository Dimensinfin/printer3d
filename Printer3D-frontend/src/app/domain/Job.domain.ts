import { Part } from './Part.domain';

export class Job {
    public id: string;
    public part: Part;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
