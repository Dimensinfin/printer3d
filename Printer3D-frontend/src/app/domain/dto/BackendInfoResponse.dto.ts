export class BackendInfoResponse {
    public version: string;
    private build: any;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }

    public transformInput(): void {
        this.version = this.build.version;
    }
}
