import { Component, Input, OnInit } from '@angular/core';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { V1ClosedRequestsPageComponent } from '../../pages/v1-closed-requests-page/v1-closed-requests-page.component';

@Component({
  selector: 'v1-closed-requests-panel',
  templateUrl: './v1-closed-requests-panel.component.html',
  styleUrls: ['./v1-closed-requests-panel.component.scss']
})
export class V1ClosedRequestsPanelComponent extends AppPanelComponent implements OnInit {
    @Input() page: V1ClosedRequestsPageComponent;

  constructor() {super()}

  ngOnInit(): void {
  }

}
