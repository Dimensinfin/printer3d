export interface ISelectable {
    toggleSelected(): boolean
    isSelected(): boolean
    select(): void
    unselect(): void
    equalRef(target: ISelectable): boolean
    getUniqueId(): string
}
