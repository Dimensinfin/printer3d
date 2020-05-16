export class Feature {
    public label: string = '/';
    public active: boolean = false;
    public route : string='/';

    constructor(values: Object = {}) {
        // super();
        Object.assign(this, values);
        // this.jsonClass = "Feature";
    }
    public getRoute() : string {
        return this.route;
    }
    public equals(target: Feature): boolean {
        if (this.label != target.label) return false;
        if (this.active != target.active) return false;
        return true;
    }
    public activate () : boolean{
        const result = this.active;
        this.active=true;
        return result;
    }
    public deactivate () : boolean{
        const result = this.active;
        this.active=false;
        return result;
    }
}
