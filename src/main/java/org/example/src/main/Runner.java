package org.example.src.main;


import org.example.src.structures.BigFASTA;
import org.example.src.structures.BigFASTAEntry;
import org.example.src.structures.FASTA;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Runner {
    public static void main(String[] args) {
        Path p = Paths.get("/home/valentin-rexer/uni/own/smaller.fa");
        BigFASTA bf = new BigFASTA(p);
        Path outPath = Paths.get("/home/valentin-rexer/uni/own/out.bfo");
        bf.saveToFile(outPath);

        BigFASTA bf2 = new BigFASTA();
        bf2.readFromFile(outPath);

        bf2.removeEntry(">entry1");

        System.out.println(bf2);
        bf2.sort(true);
        System.out.println(bf2);
    }
}
