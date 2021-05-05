// - CORE
import { Input } from '@angular/core'
import { Component } from '@angular/core'
// - DOMAIN
import { Extraction } from '@domain/extraction/Extraction.domain'

@Component({
    selector: 'v1-extraction',
    templateUrl: './v1-extraction.component.html',
    styleUrls: ['./v1-extraction.component.scss']
})
export class V1ExtractionComponent {
    @Input() node: Extraction

    public getUniqueId(): string {
        if (this.node) return this.node.getUniqueId()
        else return '-'
    }
    public getLabel(): string {
        if (this.node) return this.node.getLabel()
        else return '-'
    }
    public performExtraction(): string {
        if (this.node) return this.node.getLink()
        return '/extractions'
    }
}
