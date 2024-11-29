// - CORE
import { Observable } from 'rxjs'
import { BehaviorSubject } from 'rxjs'

export class ActiveContainerWrapper<T> {
	private _subject: BehaviorSubject<T> = new BehaviorSubject<T>(null)
	private _downloader: any
  /** @deprecated */
	private _locator: any

  /** @deprecated */
	private _loadState: boolean = false
  /** @deprecated */
	private currentData: T

  /** @deprecated */
	private _timedCache: boolean = false
  /** @deprecated */
	private _expirationTime: number = 60 * 5 // Number of seconds data is valid on cache. 5 minutes.
  /** @deprecated */
	private _canReturnObsoletes: boolean = true
	// private _cache = this._subject.asObservable()
  /** @deprecated */
	private _lastUpdateTime = new Date()

	// - C O N T E N T   M A I N T E N A N C E
  /** @deprecated */
	public build(): ActiveContainerWrapper<T> {
		return this.refresh()
	}
	public accessContainer(): Observable<T> {
		return this._subject.asObservable()
	}
	public refresh(): ActiveContainerWrapper<T> {
		return this.fireDownloader()
	}

	private fireDownloader(): ActiveContainerWrapper<T> {
		console.log('>[Containers.10]')
		console.log('>[ActiveCacheWrapper.fireDownloader]')
		// Start a new download and return the new observable to the caller.
		this._downloader().subscribe(
			data => {
				// Store into the subject the new data. But only if not null.
				this.storeData(data)
			},
			error => console.log(error),
			() => console.log('Subscription finished'),
		)
    return this
	}
	private storeData(_newdata: T): void {
		this._subject.next(_newdata)
	}

	// - G E T T E R S   &   S E T T E R S
	public setTimedCache(_newstate: boolean): ActiveContainerWrapper<T> {
		this._timedCache = _newstate
		return this
	}
	public setCachingTime(_newtime: number): ActiveContainerWrapper<T> {
		this._expirationTime = _newtime
		return this
	}
	public setReturnObsoletes(_newstate: boolean): ActiveContainerWrapper<T> {
		this._canReturnObsoletes = _newstate
		return this
	}
	public setDownloader(_downloadCall: any): ActiveContainerWrapper<T> {
		this._downloader = _downloadCall
		return this
	}
	public setLocator(_downloadLocator: any): ActiveContainerWrapper<T> {
		this._locator = _downloadLocator
		return this
	}
}
