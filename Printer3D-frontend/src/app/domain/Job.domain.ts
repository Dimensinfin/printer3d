// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from './Node.domain';
import { Part } from './Part.domain';

export class Job extends Node {
    public id: string;
    public part: Part;
    private priority: number = 3
    private count: number = 1

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
    public getNumber(): number {
        return this.count
    }
    public incrementCount(): void {
        this.count++
    }
    private transform(): void {
        if (null != this.part)
            this.part = new Part(this.part)
    }
}
