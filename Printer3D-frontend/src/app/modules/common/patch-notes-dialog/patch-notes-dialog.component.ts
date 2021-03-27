// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Coil } from '@domain/inventory/Coil.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialogRef } from '@angular/material/dialog';
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
import { PatchChange } from '@domain/PatchChange.domain';

@Component({
    selector: 'patch-notes-dialog',
    templateUrl: './patch-notes-dialog.component.html',
    styleUrls: ['./patch-notes-dialog.component.scss']
})
export class PatchNotesDialogComponent extends AppPanelComponent implements OnInit, Refreshable {

    constructor(
        public dialogRef: MatDialogRef<PatchNotesDialogComponent>,
        protected httpWrapper : HttpClientWrapperService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[PatchNotesDialogComponent.ngOnInit]");
        this.startDownloading();
        this.setVariant(EVariant.PATCH_NOTES)
        this.refresh();
        console.log("<[PatchNotesDialogComponent.ngOnInit]");
    }
    // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.processPathNotes()
    }
    private processPathNotes(): void {
        console.log(">[PatchNotesDialogComponent.processPathNotes]");
        this.backendConnections.push(
            this.httpWrapper.wrapHttpRESOURCECall('/assets/properties/LatestPatchNotes.notes.json')
                .subscribe((changes: any) => {
                    console.log('-[PatchNotesDialogComponent.processPathNotes]> Nodes downloaded: ' + changes.length);
                    const patchNotes =[                    ]
                    for (const note of changes) {
                        patchNotes.push(new PatchChange ( note))
                    }
                    this.completeDowload(patchNotes)  // Notify the completion of the download.
                })
        )
        console.log("<[PatchNotesDialogComponent.processPathNotes]");

    }
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close();
    }

}
