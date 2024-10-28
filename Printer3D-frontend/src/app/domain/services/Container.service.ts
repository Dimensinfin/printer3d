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
		this.coilsContainer = new ActiveCacheWrapper<Coil[]>()
			.setTimedCache(false) // Contents do not expire.
			.setReturnObsoletes(false) // If the content is expired then wait for a new request.
			.setDownloader((): Observable<Coil[]> => {
				return this.inventoryService.apiv2_InventoryGetCoils()
			})
      .build()
	}

	public connectCoils(): ActiveCacheWrapper<Coil[]> {
		return this.coilsContainer
	}
	public fireUpdate(target: ActiveCacheWrapper<any>): void {
		target.refresh()
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
