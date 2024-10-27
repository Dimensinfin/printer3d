import { Injectable } from '@angular/core'
import { Coil } from '@domain/inventory/Coil.domain'
import { BehaviorSubject, Observable, Subscription } from 'rxjs'

@Injectable({
	providedIn: 'root',
})
export class ContainerService {
	private coilsSubject: BehaviorSubject<Coil[]> =new BehaviorSubject<Coil[]>(null);

	public connectCoils(): Observable<Coil[]>{
		return this.coilsSubject.asObservable()
	}
	public updateCoils(newCoils: Coil[]): void {
		this.coilsSubject.next(newCoils)
	}
}
