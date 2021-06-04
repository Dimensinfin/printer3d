/**
 * Declares the different display identifiers that can be used to differentiate different renders for a same entity class. With the use of 'variants' the same model can be rendered differently depending on the view panel where it is stored.
*/
export enum EVariant {
    DEFAULT = '-DEFAULT-',
    COIL_LIST = '-COIL-LIST-',
    COIL_EDITING='-COIL-EDITING-',
    JOB_LIST = '-JOB-LIST-',
    REQUEST_PART_LIST = '-REQUEST-PART-LIST-',
    EDITABLE_PART = '-EDITABLE-PART-',
    OPEN_REQUESTS = '-OPEN-REQUESTS-',
    BUILDING_JOB = '-BUILDING-JOB-',
    DETAIL4REQUEST = '-DETAIL4REQUEST-',
    NEW_MODEL = '-NEW-MODEL-',
    SELLABLE_ITEMS = '-SELLABLE-ITEMS-',
    MODEL_COMPOSITION = '-MODEL-COMPOSITION-',
    CATALOG = '-CATALOG-',
    EDITING_PART_GROUP = '-EDITING-PART-GROUP-',
    NEW_REQUEST = '-NEW-REQUEST-',
    PATCH_NOTES='-PATCH-NOTES-LIST-',
    CHANGE='-CHANGE-',
    NOTE='-NOTE-'
}
// export enum EInteraction {
//     ACTION = 'ACTION',
//     DIALOG = 'DIALOG',
//     PAGEROUTE = 'PAGEROUTE'
// }
export enum ESeparator {
    RED = 'RED'
    , ORANGE = 'ORANGE'
    , YELLOW = 'YELLOW'
    , GREEN = 'GREEN'
    , BLUE = 'BLUE'
    , WHITE = 'WHITE'
    , BLACK = 'BLACK'
    , EMPTY = 'EMPTY'
    , SPINNER = 'SPINNER'
}
export enum RequestState {
    OPEN = 'OPEN',
    COMPLETED = 'COMPLETED',
    CLOSED = 'CLOSED'
}
export enum RequestContentType {
    EXCEPTION = 'EXCEPTION',
    PART = 'PART',
    MODEL = 'MODEL'
}
