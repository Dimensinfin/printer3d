import { Injectable } from '@angular/core'
import { InventoryService } from '@app/modules/inventory/service/inventory.service'
import { Observable } from 'rxjs'
import { ActiveContainerWrapper } from './ActiveCacheWrapper'

import { Coil } from '@domain/inventory/Coil.domain'

@Injectable({
	providedIn: 'root',
})
export class ContainerService {
	private coilsContainer: ActiveContainerWrapper<Coil[]>

	constructor(private readonly inventoryService: InventoryService) {}

  public fireUpdate(target: ActiveContainerWrapper<any>): void {
		target.refresh()
	}

	public connectCoils(): ActiveContainerWrapper<Coil[]> {
		return this.coilsContainer
	}
  private createContainers():void{
		this.coilsContainer = new ActiveContainerWrapper<Coil[]>()
			.setTimedCache(false) // Contents do not expire.
			.setReturnObsoletes(false) // If the content is expired then wait for a new request.
			.setDownloader((): Observable<Coil[]> => {
				return this.inventoryService.apiv2_InventoryGetCoils()
			})
  }
}
