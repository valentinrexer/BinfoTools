package org.example.src.structures;

public class BigFASTAEntry {
    private String id;
    private LongPair position;

    public BigFASTAEntry(String id, LongPair position) {
        this.id = id;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LongPair getPosition() {
        return position;
    }

    public void setPosition(LongPair position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return id + "\t" + position.toString();
    }
}
