// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
// - SERVICES
// - DOMAIN
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { EVariant } from '@domain/interfaces/EPack.enumerated'
import { MatDialogRef } from '@angular/material/dialog'
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service'
import { PatchChange } from '@domain/PatchChange.domain'

@Component({
    selector: 'patch-notes-dialog',
    templateUrl: './patch-notes-dialog.component.html',
    styleUrls: ['./patch-notes-dialog.component.scss']
})
export class PatchNotesDialogComponent extends AppPanelComponent implements OnInit, Refreshable {
    constructor(
        public dialogRef: MatDialogRef<PatchNotesDialogComponent>,
        protected httpWrapper: HttpClientWrapperService) {
        super()
    }

    public ngOnInit(): void {
        console.log(">[PatchNotesDialogComponent.ngOnInit]")
        this.setVariant(EVariant.PATCH_NOTES)
        super.ngOnInit()
        console.log("<[PatchNotesDialogComponent.ngOnInit]")
    }

    // - I N T E R A C T I O N S
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close()
    }

    // - I R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
        this.processPathNotes()
    }
    private processPathNotes(): void {
        console.log(">[PatchNotesDialogComponent.processPathNotes]")
        this.backendConnections.push(
            this.httpWrapper.wrapHttpRESOURCECall('/assets/changenotes/LatestPatchNotes.notes.json')
                .subscribe((changes: any) => {
                    console.log('-[PatchNotesDialogComponent.processPathNotes]> Nodes downloaded: ' + changes.length)
                    const patchNotes = []
                    for (const note of changes) {
                        patchNotes.push(new PatchChange(note))
                    }
                    this.completeDowload(patchNotes)  // Notify the completion of the download.
                })
        )
        console.log("<[PatchNotesDialogComponent.processPathNotes]")
    }
}
