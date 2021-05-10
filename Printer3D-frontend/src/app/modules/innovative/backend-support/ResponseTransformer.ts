export class ResponseTransformer {
    public description: string = 'No transformation description.'
    public transform: any

    public setDescription(_newdescription: string): ResponseTransformer {
        this.description = _newdescription
        return this
    }
    public setTransformation(_function: any): ResponseTransformer {
        this.transform = _function
        return this
    }
}
