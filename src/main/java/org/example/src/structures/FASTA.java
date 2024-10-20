package org.example.src.structures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FASTA {
    private List<FASTAEntry> entries;
    private Logger LOG = LogManager.getLogger(FASTA.class);

    public FASTA() {
        entries = new ArrayList<>();
    }

    public FASTA(Path filePath) {
        entries = new ArrayList<>();
        store(filePath);
    }

    public FASTA(List<Path> files) {
        entries = new ArrayList<>();

        for (Path file : files) {
            store(file);
        }
    }

    public void store(Path filePath) {
        LOG.info("Storing FASTA file: " + filePath.toAbsolutePath());
        long counter = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()));
            String line;
            String name = "";
            String sequence = "";

            while ((line = br.readLine()) != null) {

                // ignore comment lines
                if(line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                if(line.startsWith(">")){

                    if(!name.isEmpty()) {
                        entries.add(new FASTAEntry(name, sequence));
                        sequence = "";
                        counter++;
                        if(counter % 1000 == 0){
                            LOG.info("Stored {} entries from {}", counter, filePath.toAbsolutePath());
                        }
                    }

                    name = line.strip();
                }

                else sequence = sequence.concat(line.strip());
            }

            entries.add(new FASTAEntry(name, sequence));
            LOG.info("Stored {} entries from {}", counter, filePath.toAbsolutePath());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(String id, String sequence) {
        entries.add(new FASTAEntry(id, sequence));
    }

    public void addEntry(FASTAEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(String id) {
        entries.removeIf(entry -> entry.getId().equals(id));
    }

    public void toFile(Path outPath, boolean clear) {
        try {
            if(clear) {
                BufferedWriter clearWriter = new BufferedWriter(new FileWriter(outPath.toFile()));
                clearWriter.write("");
                clearWriter.close();
            }

            BufferedWriter br = new BufferedWriter(new FileWriter(outPath.toFile(), true));
            for(FASTAEntry entry : entries) {
                br.write(entry.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long size() {
        return entries.size();
    }

    public List<FASTAEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (FASTAEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }
        return sb.toString();
    }
}
