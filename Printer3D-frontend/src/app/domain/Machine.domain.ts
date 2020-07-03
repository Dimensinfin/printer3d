// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { Node } from './Node.domain';
import { IContent } from './interfaces/IContent.interface';
import { RequestContentType } from './interfaces/EPack.enumerated';

export class Machine extends Node {
    private id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public buildRecord: any;
    // Deprecated. Machine version 1
    // public currentJobPart: Part;
    // public currentPartInstances: number = 1;
    // public jobInstallmentDate: string;

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
