// - DOMAIN
import { Node } from '../Node.domain';

export class Machine extends Node {
    private id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public buildRecord: any;

    constructor(values: Object = {}) {
        super()
        Object.assign(this, values);
    }

    public getId(): string {
        return this.id;
    }
    public isRunning(): boolean {
        if (null != this.buildRecord)
            if (this.buildRecord.state === 'RUNNING') return true;
        return false;
    }
    public getRunTime(): number {
        if (this.isRunning()) {
            return this.buildRecord.remainingTime;
        }
        return 0;
    }
}
