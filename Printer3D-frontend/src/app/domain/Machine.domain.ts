import { Part } from './Part.domain';

export class Machine {
    public id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public currentJobPartId: Part;
    public currentPartInstances:number =1;
    public jobInstallmentDate:string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }
    private transformInput(): void {
        if (null != this.currentJobPartId) {
            this.currentJobPartId = new Part(this.currentJobPartId)
        }
    }

}
