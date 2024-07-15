package ae.dubai.buid.cs.cs314.bt;

public class HashTable<E extends Comparable<E>> {

    private HashNode<E>[] table;
    private int size;

    public HashTable(int size) {
        table = new HashNode[size];
        this.size = size;
    }

    public HashNode<E>[] getTable() {
        return table;
    }

    public boolean search(E element) {
        int hash = getHash(element);
        if (table[hash] == null) {
            table[hash] = new HashNode<>();
            return false;
        }
        return table[hash].search(table[hash].getRoot(), element);
    }

    private int getHash(E element) {
        int hash = element.hashCode() % size;
        hash = Math.abs(hash);
        return hash;
    }
}
