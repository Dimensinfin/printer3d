import { MatDialogHarness } from '@angular/material/dialog/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { SupportMatDialogRef } from './SupporMatDialogRef.component';

export declare class SupportMatDialogHarness<T> extends MatDialogHarness {
    public open(dialogComponent: any, config: object): SupportMatDialogRef<T> {
        return new SupportMatDialogRef<T>();
    }
}
