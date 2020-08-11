// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackendService } from '@app/services/backend.service';
import { ISelection } from '@domain/interfaces/core/ISelection.interface';
import { ISelectable } from '@domain/interfaces/core/ISelectable.interface';

export class SingleSelection implements ISelection {
    private target: ISelectable;

    public getSelection(): ISelectable[] {
        return [this.target]
    }
    public getFirstSelected(): ISelectable {
        return this.target
    }
    public clearSelection(): void {
        if (null != this.target) {
            this.target.unselect()
            this.target = null
        }
    }
    public addSelection(node: ISelectable): void {
        if (null != this.target)
            this.clearSelection()
        this.target = node
        node.select()
    }
    public subtractSelection(node: ISelectable): boolean {
        if (null != this.target) {
            if (node.equalRef(this.target)) {
                this.clearSelection()
                return true
            }
        }
        return false
    }
}
