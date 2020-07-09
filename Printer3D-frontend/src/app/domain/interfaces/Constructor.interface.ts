export interface Constructor<T> {
    convert(input: any): T;
}
