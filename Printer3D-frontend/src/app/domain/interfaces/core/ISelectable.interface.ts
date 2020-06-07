export interface ISelectable  {
  toggleSelected(): boolean;
  isSelected(): boolean;
  select(): void;
  unselect(): void
}
