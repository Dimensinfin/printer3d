export class GridColumn {
    public headerName: string;
    public field: string;
    public sortable: boolean = true;
    public filter: boolean = false;
    public checkboxSelection: boolean = false;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
