import { Component, OnInit } from '@angular/core';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { ContainerService } from '@domain/services/Container.service';

@Component({
  selector: 'v2-coils-panel',
  templateUrl: './v2-coils-panel.component.html',
  styleUrls: ['./v2-coils-panel.component.scss']
})
export class V2CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {

  constructor(protected readonly containerService: ContainerService) {
    super();
  }

  ngOnInit(): void {
  }

}
