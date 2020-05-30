export interface Transformer<S, D> {
    transform(source: S): D
}
