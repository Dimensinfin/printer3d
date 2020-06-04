import { Part } from './Part.domain';

export class Machine {
    public id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public currentJobPart: Part;
    public currentPartInstances: number = 1;
    public jobInstallmentDate: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }
    private transformInput(): void {
        if (null != this.currentJobPart) {
            this.currentJobPart = new Part(this.currentJobPart)
        }
    }
    public isRunning(): boolean {
        if (null != this.currentJobPart) return true;
        else return false;
    }
    public getRunTime(): number {
        if (this.isRunning()) {
            // Calculate the remaining time from the installment time.
            return Math.abs(Date.parse(this.jobInstallmentDate) - Date.now()) / 1000 / 60;
        }
    }
}
