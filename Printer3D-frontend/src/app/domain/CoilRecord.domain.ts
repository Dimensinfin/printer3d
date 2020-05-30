export class CoilRecord{
    public material:string;
    public color:string;
    public peso:string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
