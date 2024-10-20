package org.example.src.structures;

import java.util.Objects;

public class LongPair {
    private long long1;
    private long long2;

    public LongPair(long long1, long long2) {
        this.long1 = long1;
        this.long2 = long2;
    }

    public long getLong1() {
        return long1;
    }

    public void setLong1(long long1) {
        this.long1 = long1;
    }

    public long getLong2() {
        return long2;
    }

    public void setLong2(long long2) {
        this.long2 = long2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongPair longPair = (LongPair) o;
        return long1 == longPair.long1 && long2 == longPair.long2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(long1, long2);
    }

    @Override
    public String toString() {
        return "(" + long1 + ", " + long2 + ")";
    }
}
