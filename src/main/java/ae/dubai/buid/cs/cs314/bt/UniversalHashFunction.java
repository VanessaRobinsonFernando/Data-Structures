package ae.dubai.buid.cs.cs314.bt;

public class UniversalHashFunction<K> {
    private int a;
    private int b;
    private int p;
    private int m;

    public UniversalHashFunction(int a, int b, int p, int m) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.m = m;
    }

    public int hash(K key) {
        int k = key.hashCode();
        int hashValue = ((a + b * k) % p) % m;

        // Ensure the result is non-negative
        return (hashValue < 0) ? hashValue + p : hashValue;
    }
}
