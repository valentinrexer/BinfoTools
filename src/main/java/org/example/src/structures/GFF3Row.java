package org.example.src.structures;

import org.example.src.customenum.Strand;

public class GFF3Row {
    private String seqId;
    private String source;
    private String type;
    private long start;
    private long end;
    private double score;
    private Strand strand;
    private int phase;
    private String attributes;

    public GFF3Row(String rowAsString) {
        String[] tokens = rowAsString.split(" ");
        if(tokens.length != 9) {
            throw new IllegalArgumentException("Row contains invalid format");
        }

        seqId = tokens[0];
        source = tokens[1];
        type = tokens[2];
        start = Long.parseLong(tokens[3]);
        end = Long.parseLong(tokens[4]);
        score = Double.parseDouble(tokens[5]);
        if(tokens[6].charAt(0) == '.')
            strand = null;

        else strand = tokens[6].charAt(0) == '+' ? Strand.FORWARD : Strand.REVERSE;

        phase = Integer.parseInt(tokens[7]);
        attributes = tokens[8];

    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Strand getStrand() {
        return strand;
    }

    public void setStrand(Strand strand) {
        this.strand = strand;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
