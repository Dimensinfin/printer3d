export class BackendInfoResponse {
    // private version: string;
    private build: any;
    // private ff: string="gghwfj"

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    public getVersion(): string {
        return '<' + this.build.version + ' backend';
    }
}
