package ae.dubai.buid.cs.cs314.bt;

import java.util.BitSet;
import java.util.Random;

public class BloomFilter<K> {
    private int k; // the number of hash functions
    private int m; // the size of each vector
    private BitSet[] vectors;
    private UniversalHashFunction<K>[] hashFunctions;

    public BloomFilter(int hashFunctionsCount, int vectorLength) {
        this.k = hashFunctionsCount;
        this.m = vectorLength;

        // Initialize bit vectors
        vectors = new BitSet[k];
        for (int i = 0; i < k; i++) {
            vectors[i] = new BitSet(m);
        }

        // Initialize hash functions
        hashFunctions = new UniversalHashFunction[k];
        Random random = new Random();

        for (int i = 0; i < k; i++) {
            int a = random.nextInt(Integer.MAX_VALUE);
            int b = random.nextInt(Integer.MAX_VALUE);
            int p = getPrimeGreaterThan(m); // Get a prime number greater than m
            hashFunctions[i] = new UniversalHashFunction<>(a, b, p, m);
        }
    }

    public void add(K key) {
        for (int i = 0; i < k; i++) {
            int hashValue = hashFunctions[i].hash(key);
            vectors[i].set(hashValue);
        }
    }

    public boolean contains(K key) {
        for (int i = 0; i < k; i++) {
            int hashValue = hashFunctions[i].hash(key);
            if (!vectors[i].get(hashValue)) {
                return false; // If any vector doesn't contain the hash, the key is not present
            }
        }
        return true; // All vectors contain the hash, the key may be present
    }

    // Helper method to get a prime number greater than the given number
    private int getPrimeGreaterThan(int n) {
        int prime = n + 1;
        while (!isPrime(prime)) {
            prime++;
        }
        return prime;
    }

    // Helper method to check if a number is prime
    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
