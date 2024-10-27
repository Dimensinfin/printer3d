import { Component, OnInit } from '@angular/core'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { Coil } from '@domain/inventory/Coil.domain'
import { ContainerService } from '@domain/services/Container.service'

@Component({
	selector: 'v2-coils-panel',
	templateUrl: './v2-coils-panel.component.html',
	styleUrls: ['./v2-coils-panel.component.scss'],
})
export class V2CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
	constructor(protected readonly containerService: ContainerService) {
		super()
	}

	ngOnInit(): void {
		console.log('>[V2CoilsPanelComponent.ngOnInit]')
		super.ngOnInit()
		console.log('<[V2CoilsPanelComponent.ngOnInit]')
	}

	// - R E F R E S H A B L E
	public clean(): void {}
	public refresh(): void {
		this.clean()
		this.connectCoils()
	}
	/**
	 * Connect to the Coils data container provider to listen for any chnage on the list of Coils
	 */
	private connectCoils() {
		console.log('>[V2CoilsPanelComponent.connectCoils]')
		this.backendConnections.push(
			this.containerService.connectCoils().subscribe(
				data => console.log(data),
				error => console.log(error),
				() => console.log('Subscription finished'),
			),
		)
		console.log('<[V2CoilsPanelComponent.connectCoils]')
	}
}
