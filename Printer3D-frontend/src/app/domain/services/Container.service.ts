import { Injectable } from '@angular/core'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'
import { Coil } from '@domain/inventory/Coil.domain'
import { BehaviorSubject, Observable, Subscription } from 'rxjs'
import { ActiveCacheWrapper } from './ActiveCacheWrapper'

@Injectable({
	providedIn: 'root',
})
export class ContainerService {
	private coilsContainer: ActiveCacheWrapper<Coil[]>

	constructor(private readonly inventoryService: InventoryService) {
		console.log('>[Containers.01]')
		this.coilsContainer = new ActiveCacheWrapper<Coil[]>()
			.setTimedCache(false) // Contents do not expire.
			.setReturnObsoletes(false) // If the content is expired then wait for a new request.
			.setDownloader((): Observable<Coil[]> => {
        console.log('>[Containers.11]')
        return this.inventoryService.apiv2_InventoryGetCoils()
			})
      // .build()
	}

	public connectCoils(): ActiveCacheWrapper<Coil[]> {
		console.log('>[Containers.05]')
		return this.coilsContainer
	}
	public fireUpdate(target: ActiveCacheWrapper<any>): void {
    console.log('>[Containers.08]')
    this.coilsContainer.refresh()
	}
	/**
	 *@deprecated
	 * @param newCoils
	 */
	public updateCoils(newCoils: Coil[]): void {
		this.coilsContainer.updateContents(newCoils)
	}
	/**
	 * @deprecated
	 * @param coilList
	 */
	public accessCoils(coilList: Coil[]) {
		this.coilsContainer.updateContents(coilList)
	}
	private downloadCoils() {
		this.inventoryService.apiv2_InventoryGetCoils().subscribe(data => this.coilsContainer.updateContents(data))
	}
}
