export interface IExpandable {
    // expanded: boolean;

    isExpandable(): boolean;
    isExpanded(): boolean;
    collapse(): boolean;
    expand(): boolean;
    toggleExpanded();
    // getContentsSize(): number;
}
