package ae.dubai.buid.cs.cs314.bt;
import ae.dubai.buid.cs.cs314.bt.HashNode.AVLNode;

import java.util.Random;

public class CuckooNode<E extends Comparable<E>> {
    private static final int INITIAL_TABLE_SIZE = 16; // Initial size of the hash tables
    private static final int MAX_KICK_OUTS = 5; // Maximum number of kick-outs before rehashing

    private AVLNode<E>[] table1;
    private AVLNode<E>[] table2;
    private int tableSize;
    private Random random;
    private boolean isRehashing = false;

    public CuckooNode() {
        this.tableSize = INITIAL_TABLE_SIZE;
        this.table1 = new AVLNode[tableSize];
        this.table2 = new AVLNode[tableSize];
        this.random = new Random();
    }

    // Rest of the AVLNode class remains the same

    // Helper method to compute hash using the first hash function
    private int hashFunction1(E element) {
        return Math.abs(element.hashCode()) % tableSize;
    }

    // Helper method to compute hash using the second hash function
    private int hashFunction2(E element) {
        return Math.abs(element.hashCode() * 31) % tableSize; // Using a different prime multiplier
    }

    // Method to rehash elements when the table size needs to be increased
    private void rehash() {
        // Check if rehashing is already in progress
        if (isRehashing) {
            return;
        }

        // Set the flag to indicate that rehashing is in progress
        isRehashing = true;

        int oldSize = tableSize;
        tableSize *= 2;
        AVLNode<E>[] oldTable1 = table1;
        AVLNode<E>[] oldTable2 = table2;

        // Initialize new tables with the updated size
        table1 = new AVLNode[tableSize];
        table2 = new AVLNode[tableSize];

        // Reinsert elements from the old tables into the new tables
        for (int i = 0; i < oldSize; i++) {
            if (oldTable1[i] != null) {
                insertCuckoo(oldTable1[i].getElement(), MAX_KICK_OUTS);
            }
            if (oldTable2[i] != null) {
                insertCuckoo(oldTable2[i].getElement(), MAX_KICK_OUTS);
            }
        }

        // Reset the flag after rehashing is complete
        isRehashing = false;
    }

    // Insert element using Cuckoo hashing
    public void insertCuckoo(E element) {
        insertCuckoo(element, MAX_KICK_OUTS);
    }

    private void insertCuckoo(E element, int kickOuts) {
        if (kickOuts == 0) {
            // If maximum kick-outs reached, rehash and try again
            rehash();
            insertCuckoo(element, MAX_KICK_OUTS);
            return;
        }

        int hash1 = hashFunction1(element);
        int hash2 = hashFunction2(element);

        // Try to insert into the first table
        if (table1[hash1] == null) {
            table1[hash1] = new AVLNode<>(element);
        } else {
            // If occupied, kick out the existing element and try to insert it into the second table
            AVLNode<E> kickedOut = table1[hash1];
            table1[hash1] = new AVLNode<>(element);
            insertCuckoo(kickedOut.getElement(), kickOuts - 1);
        }

        // Try to insert into the second table
        if (table2[hash2] == null) {
            table2[hash2] = new AVLNode<>(element);
        } else {
            // If occupied, kick out the existing element and try to insert it into the first table
            AVLNode<E> kickedOut = table2[hash2];
            table2[hash2] = new AVLNode<>(element);
            insertCuckoo(kickedOut.getElement(), kickOuts - 1);
        }
    }

    public AVLNode<E>[] getTable1() {
        return table1;
    }

    public AVLNode<E>[] getTable2() {
        return table2;
    }
}
