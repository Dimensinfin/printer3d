export class Node {
    public jsonClass: string = 'Node';

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.jsonClass = 'Node';
    }
}
