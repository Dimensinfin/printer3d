export class SupportLocalStorage {
    private storage = new Map();
    
    public get(key: string): any {
        return this.storage.get(key);
    }
    public set(key: string, content: any): any {
        return this.storage.set(key, content)
    }
    public remove(key: string): void {
        this.storage.set(key, null);
    }
}
