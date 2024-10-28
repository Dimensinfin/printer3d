import { Component, OnInit } from '@angular/core'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { Coil } from '@domain/inventory/Coil.domain'
import { ActiveCacheWrapper } from '@domain/services/ActiveCacheWrapper'
import { ContainerService } from '@domain/services/Container.service'
import { BehaviorSubject, Observable } from 'rxjs'

@Component({
	selector: 'v2-coils-panel',
	templateUrl: './v2-coils-panel.component.html',
	styleUrls: ['./v2-coils-panel.component.scss'],
})
export class V2CoilsPanelComponent extends AppPanelComponent implements OnInit, Refreshable {
	private containerCoils: ActiveCacheWrapper<Coil[]>

	constructor(protected readonly containerService: ContainerService) {
		super()
	}

	ngOnInit(): void {
		console.log('>[Containers.02]')
		console.log('>[V2CoilsPanelComponent.ngOnInit]')
		super.ngOnInit()
		console.log('<[V2CoilsPanelComponent.ngOnInit]')
	}

	// - R E F R E S H A B L E
	public clean(): void {}
	public refresh(): void {
		console.log('>[Containers.03]')
		console.log('>[V2CoilsPanelComponent.refresh]')
		this.clean()
		this.connectCoils()
		this.fireUpdate()
		console.log('<[V2CoilsPanelComponent.refresh]')
	}
	/**
	 * Connect to the Coils data container provider to listen for any chnage on the list of Coils
	 */
	private connectCoils() {
		console.log('>[Containers.04]')
		console.log('>[V2CoilsPanelComponent.connectCoils]')
		this.containerCoils = this.containerService.connectCoils()
		const containerSubscription: Observable<Coil[]> = this.containerCoils.accessContainer()
		this.backendConnections.push(
			containerSubscription.subscribe(
				data => {
					if (data instanceof Array) this.completeDowload(data)
				},
				error => console.log(error),
				() => console.log('Subscription finished'),
			),
		)
		console.log('<[V2CoilsPanelComponent.connectCoils]')
	}
	private fireUpdate(): void {
		console.log('>[Containers.07]')
		this.containerCoils.refresh()
	}
}
