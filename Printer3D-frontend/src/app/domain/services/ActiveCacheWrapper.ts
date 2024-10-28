// - CORE
import { Observable } from 'rxjs'
import { BehaviorSubject } from 'rxjs'
import { map } from 'rxjs/operators'
import { addMilliseconds } from 'date-fns'
import { differenceInMilliseconds } from 'date-fns'
import { deprecate } from 'util'

export class ActiveCacheWrapper<T> {
	private _loadState: boolean = false
	private currentData: T

	private _timedCache: boolean = false
	private _expirationTime: number = 60 * 5 // Number of seconds data is valid on cache. 5 minutes.
	private _canReturnObsoletes: boolean = true
	private _subject: BehaviorSubject<T> = new BehaviorSubject<T>(null)
	// private _cache = this._subject.asObservable()
	private _lastUpdateTime = new Date()
	private _downloader: any
	private _locator: any

	// - C O N T E N T   M A I N T E N A N C E
	public build(): ActiveCacheWrapper<T> {
		return this.refresh()
	}
	public accessContainer(): Observable<T> {
		console.log('>[Containers.06]')
		return this._subject.asObservable()
	}
	public refresh(): ActiveCacheWrapper<T> {
		// console.log('-[ActiveCacheWrapper.refresh]> _loadState: ' + this._loadState)
		// if (this._loadState) {
		// 	this._subject.next(this.currentData)
		// } else {
      console.log('>[Containers.09]')
			this.fireDownloader()
		// }
		return this
	}

	private fireDownloader(): Observable<T> {
    console.log('>[Containers.10]')
    console.log('>[ActiveCacheWrapper.fireDownloader]')
		// Start a new download and return the new observable to the caller.
		return this._downloader().pipe(
			map((data: T) => {
        console.log('>[Containers.20]')
        // Store into the subject the new data. But only if not null.
				console.log('>[ActiveCacheWrapper.fireDownloader]> data: ' + JSON.stringify(data))
				if (null == data) {
					this._subject.next(data)
					this._loadState = false
					return data
				} else {
					this.storeData(data)
					return data
				}
			}),
		)
	}
	private storeData(_newdata: T): void {
		this._loadState = true
		this._lastUpdateTime = new Date()
		this._subject.next(_newdata)
	}

	/**
	 * @deprecated
	 * @returns
	 */
	// public accessData2(): Observable<T> {
	// 	// Check if the data is ready.
	// 	console.log('-[ActiveCacheWrapper.accessData]> _loadState: ' + this._loadState)
	// 	if (this._loadState) {
	// 		// Check the cache time status.
	// 		let now = new Date()
	// 		let expirationTime = addMilliseconds(this._lastUpdateTime, this._expirationTime * 1000)
	// 		let diff = differenceInMilliseconds(expirationTime, now)
	// 		if (diff > 0) return this._cache
	// 		else {
	// 			// Cache expired. Decide if we wait or return an obsolete response.
	// 			if (this._canReturnObsoletes) {
	// 				this.fireDownloader()
	// 				return this._cache
	// 			} else return this.fireDownloader()
	// 		}
	// 	} else return this.fireDownloader()
	// }
	public clear(): void {
		this._subject.next(null)
		this._loadState = false
	}
	public accessLastData(): T {
		return this._subject.value
	}

	// - G E T T E R S   &   S E T T E R S
	public setTimedCache(_newstate: boolean): ActiveCacheWrapper<T> {
		this._timedCache = _newstate
		return this
	}
	public setCachingTime(_newtime: number): ActiveCacheWrapper<T> {
		this._expirationTime = _newtime
		return this
	}
	public setReturnObsoletes(_newstate: boolean): ActiveCacheWrapper<T> {
		this._canReturnObsoletes = _newstate
		return this
	}
	public setDownloader(_downloadCall: any): ActiveCacheWrapper<T> {
		this._downloader = _downloadCall
		return this
	}
	public setLocator(_downloadLocator: any): ActiveCacheWrapper<T> {
		this._locator = _downloadLocator
		return this
	}
  /**
   * @deprecated
   * @param data
   */
	public updateContents(data: T) {
		this._subject.next(data)
	}
}
