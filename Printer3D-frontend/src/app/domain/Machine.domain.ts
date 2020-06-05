import { Part } from './Part.domain';

export class Machine {
    public id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public buildRecord: any;
    public currentJobPart: Part;
    public currentPartInstances: number = 1;
    public jobInstallmentDate: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        // this.transformInput();
    }
    // private transformInput(): void {
    //     if (null != this.currentJobPart) {
    //         this.currentJobPart = new Part(this.currentJobPart)
    //     }
    // }
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
