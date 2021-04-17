import { Node } from "@domain/Node.domain";

export class Project extends Node {
    constructor(values: Object = {}) {
        super(values)
        this.jsonClass = 'Project'
    }
}
