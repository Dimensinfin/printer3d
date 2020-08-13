// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from '../Node.domain';
import { Part } from '../inventory/Part.domain';

export class Job extends Node {
    public id: string;
    public part: Part;
    private priority: number = 3
    private partCount: number = 1 // Stores the number of copies to build on a run.
    private aggregatedCount: number = 1 // Stores the number of jobs of the same Part so the list is reduced

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.transform();
        this.jsonClass = 'Job'
    }

    public getId(): string {
        return this.id;
    }
    public getPart(): Part {
        return this.part;
    }
    public getBuildSeconds(): number {
        return this.getPart().buildTime * 60
    }
    public getPriority(): number {
        return this.priority
    }
    public getCopies(): number {
        if (null == this.partCount) return 1
        return this.partCount
    }
    public getAggregated(): number {
        return this.aggregatedCount
    }
    public aggregate(): void {
        this.aggregatedCount++
    }
    private transform(): void {
        if (null != this.part)
            this.part = new Part(this.part)
    }
}
