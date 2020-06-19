import { Part } from './Part.domain';
import { deprecate } from 'util';

export class Machine {
    private id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public buildRecord: any;
    // Deprecated. Machine version 1
    public currentJobPart: Part;
    public currentPartInstances: number = 1;
    public jobInstallmentDate: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    
    public getId() : string{
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
