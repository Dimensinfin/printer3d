// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';

@Component({
    selector: 'v1-feature-render',
    templateUrl: './v1-feature-render.component.html',
    styleUrls: ['./v1-feature-render.component.scss']
})
export class V1FeatureRenderComponent {
    @Input() node: Feature;
}
