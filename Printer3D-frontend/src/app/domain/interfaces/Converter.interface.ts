export interface Converter<S, D> {
    convert(input: S): D;
}
