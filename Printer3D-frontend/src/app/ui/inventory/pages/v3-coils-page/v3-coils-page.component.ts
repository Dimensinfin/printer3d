import { Component, OnInit } from '@angular/core'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'
import { BackendService } from '@app/services/backend.service'
import { Coil } from '@domain/inventory/Coil.domain'
import { ContainerService } from '@domain/services/Container.service'

@Component({
	selector: 'v3-coils-page',
	templateUrl: './v3-coils-page.component.html',
	styleUrls: ['./v3-coils-page.component.scss'],
})
export class V3CoilsPageComponent implements OnInit {
	private recordCounter: number = 1
	constructor(private readonly containerService: ContainerService, private readonly inventoryService: InventoryService) {}

	public ngOnInit(): void {}

	public fireUpdate() {
		console.log('>[V3CoilsPageComponent.connectCoils]')
		// this.inventoryService.apiv2_InventoryGetCoils().subscribe((coilList: Coil[]) => {
		// 	console.log('-[V3CoilsPageComponent.connectCoils]> Nodes downloaded: ' + coilList.length)
		// 	const targetCoils: Coil[] = []
		// 	for (let index = 0; index < this.recordCounter; index++) {
		// 		targetCoils.push(coilList[index])
		// 	}
		// 	this.containerService.accessCoils(coilList)
		// })
		console.log('<[V3CoilsPageComponent.connectCoils]')
	}
}
