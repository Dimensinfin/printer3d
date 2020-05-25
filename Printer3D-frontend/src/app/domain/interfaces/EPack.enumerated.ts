/**
 * Declares the different display identifiers that can be used to differentiate different renders for a same entity class. With the use of 'variants' the same model can be rendered differently depending on the view panel where it is stored.
*/
export enum EVariant {
    DEFAULT = '-DEFAULT-'
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
