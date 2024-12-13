package ru.agniaendie.coursalwork;

// Can RW with record, but I'm using jdk 1.8
public class Pair<T, T1> {
    private final T a;
    private final T1 b;

    public Pair(T a, T1 b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T1 getB() {
        return b;
    }
}
