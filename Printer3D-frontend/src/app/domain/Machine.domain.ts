export class Machine {
    public id: string;
    public label: string;
    public model: string;
    public characteristics: string;
    
    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
