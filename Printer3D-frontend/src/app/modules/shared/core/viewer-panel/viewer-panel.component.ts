// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
// - DOMAIN
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '../app-panel/app-panel.component';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';

@Component({
    selector: 'viewer-panel',
    templateUrl: './viewer-panel.component.html',
    styleUrls: ['./viewer-panel.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewerPanelComponent {
    @Input() nodes2render: ICollaboration[] = [];
    @Input() downloadtitle: string;
    @Input() downloader: AppPanelComponent;
    @Input() variant: EVariant = EVariant.DEFAULT;
    @Input() autoselect: boolean = false
    public index: number = 1;

    public isDownloading(): boolean {
        if (null != this.downloader) return this.downloader.isDownloading();
        else return true;
    }
    public getNodes2Render(): ICollaboration[] {
        console.log('><[ViewerPanelComponent.getNodes2Render]> count: ' + this.nodes2render.length)
        return this.nodes2render;
    }
    public getNextIndex(): number {
        return this.index++;
    }
    public getVariant(): EVariant {
        return this.variant;
    }
}
