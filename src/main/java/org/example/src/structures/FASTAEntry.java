package org.example.src.structures;

import java.util.Objects;

public class FASTAEntry {
    private String id;
    private String sequence;

    public FASTAEntry(String id, String sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FASTAEntry that = (FASTAEntry) o;
        return Objects.equals(id, that.id) && Objects.equals(sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequence);
    }

    @Override
    public String toString() {
        return id + "\n" + sequence;
    }
}
