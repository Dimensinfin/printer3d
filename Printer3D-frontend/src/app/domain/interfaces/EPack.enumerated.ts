/**
 * Declares the different display identifiers that can be used to differentiate different renders for a same entity class. With the use of 'variants' the same model can be rendered differently depending on the view panel where it is stored.
*/
export enum EVariant {
    DEFAULT = '-DEFAULT-',
    PART_LIST = '-PART-LIST-',
    COIL_LIST = '-COIL-LIST-',
    JOB_LIST = '-JOB-LIST-',
    REQUEST_PART_LIST = '-REQUEST-PART-LIST-',
    REQUEST_PARTS = '-REQUEST-CONTAINED-PARTS-',
    EDITABLE_PART = '-EDITABLE-PART-',
    OPEN_REQUESTS = '-OPEN-REQUESTS-',
    DETAILED_REQUEST = '-DETAILED-REQUEST-'
}
export enum EInteraction {
    ACTION = 'ACTION',
    DIALOG = 'DIALOG',
    PAGEROUTE = 'PAGEROUTE'
}
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
