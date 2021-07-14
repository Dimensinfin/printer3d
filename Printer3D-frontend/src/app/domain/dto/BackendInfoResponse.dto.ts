export class BackendInfoResponse {
    private build: any

    constructor(values: Object = {}) {
        Object.assign(this, values)
    }

    public getVersion(): string {
        return '<' + this.build.version + ' backend'
    }
}
