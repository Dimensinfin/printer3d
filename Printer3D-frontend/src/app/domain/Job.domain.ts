export class Job {
    public image: string;
    public label: string;
    public material: string;
    public color: string;
    public buildTime: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
