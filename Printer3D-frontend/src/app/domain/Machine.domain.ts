import { Part } from './Part.domain';

export class Machine {
    public id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    public currentPart: Part;
    public jobInstallmentDate:string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }
    private transformInput(): void {
        if (null != this.currentPart) {
            this.currentPart = new Part(this.currentPart)
        }
    }

}
