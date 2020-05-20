import { MatDialogRef } from '@angular/material/dialog';
import { OverlayRef } from '@angular/cdk/overlay';

export class SupportMatDialogRef<NewPartDialogComponent> extends MatDialogRef<NewPartDialogComponent>{
    constructor() {
        super(new OverlayRef(), _containerInstance: MatDialogContainer, id?: string);
    }
    public close(): void { }
}
