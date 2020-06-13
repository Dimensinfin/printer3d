/**
 * Declares the different display identifiers that can be used to differentiate different renders for a same entity class. With the use of 'variants' the same model can be rendered differently depending on the view panel where it is stored.
*/
export enum EVariant {
    DEFAULT = '-DEFAULT-',
    PART_LIST = '-PART-LIST-',
    COIL_LIST = '-COIL-LIST-',
    REQUEST_PART_LIST = '-REQUEST-PART-LIST-',
    EDITABLE_PART = '-EDITABLE-PART-'
}
export enum EInteraction {
    ACTION = 'ACTION',
    DIALOG = 'DIALOG',
    PAGEROUTE = 'PAGEROUTE'
}
export enum EColorCode {
    UNDEFINED = 'UNDEFINED',
    WHITE = 'WHITE',
    GREEN = 'GREEN',
    GREEN_TRANSP = 'GREEN-TRANSP',
    RED = 'RED',
    LIGHT_BLUE = 'LIGHT-BLUE',
    PINK_TRANSP = 'PINK-TRANSP',
    ORANGE_TRANSP = 'ORANGE-TRANSP'
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
