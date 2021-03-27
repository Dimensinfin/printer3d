import { ICollaboration } from "./core/ICollaboration.interface";

export interface IFiltered extends ICollaboration{
    getRepresentation(): string
}
