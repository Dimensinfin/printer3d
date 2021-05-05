export class Extraction {
    private id: string
    private label: string
    private link: string

    constructor(values: Object = {}) {
        Object.assign(this, values)
    }

    public getUniqueId(): string {
        return this.id
    }
    public getLabel(): string {
        return this.label
    }
    public getLink(): string {
        return this.link
    }
}
