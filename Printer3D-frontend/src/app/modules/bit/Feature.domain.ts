export class Feature {
	label: string
	enabled: boolean
	active: boolean
	interaction: string
	route: string

	constructor(values: object = {}) {
		Object.assign(this, values)
	}
}
