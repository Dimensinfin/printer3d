export class Feature {
    public label: string = '/';
    public active: boolean = false;

    constructor(values: Object = {}) {
        // super();
        Object.assign(this, values);
        // this.jsonClass = "Feature";
    }
}
