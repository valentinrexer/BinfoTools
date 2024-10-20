package org.example.src.structures;

import org.example.src.util.Utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BigFASTA {
    List<BigFASTAEntry> entries;
    Path filePath;

    public BigFASTA() {
        entries = new ArrayList<>();
    }

    public BigFASTA(Path filePath) {
        entries = new ArrayList<>();
        this.filePath = filePath;
        store(filePath);
    }

    private void store(Path filePath) {
        try(RandomAccessFile pointer = new RandomAccessFile(filePath.toFile(), "r")) {
            String line;
            String currentID = "";
            long lastPosition = 0;

            while((line = pointer.readLine()) != null) {

                if(line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                if(line.startsWith(">")) {
                    if(!currentID.isEmpty()) {
                        LongPair position = new LongPair(lastPosition, pointer.getFilePointer() - line.length() - 1);
                        entries.add(new BigFASTAEntry(currentID, position));
                    }
                    currentID = line;
                    lastPosition = pointer.getFilePointer();
                }
            }

            LongPair position = new LongPair(lastPosition, pointer.getFilePointer());
            entries.add(new BigFASTAEntry(currentID, position));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FASTAEntry getFASTAEntry(String id) {
        for(BigFASTAEntry entry : entries) {
            if(entry.getId().equals(id)) {
                LongPair position = entry.getPosition();
                long start = position.getLong1();
                long end = position.getLong2();

                return new FASTAEntry(id, Utils.readBytesFromPosToPos(filePath, start, end, true));
            }
        }
        return null;
    }

    /**
     * Removes the first occurrence of id in the BigFASTA object
     * @param id id of the entry that's about to be removed
     */
    public void removeEntry(String id) {
        int positionToRemove = -1;
        for(int i = 0 ; i < entries.size() ; i++) {
            if(entries.get(i).getId().equals(id)) {
                positionToRemove = i;
                break;
            }
        }

        if(positionToRemove != -1) {
            entries.remove(positionToRemove);
            System.out.println("Successfully removed "+id+" at position "+positionToRemove+"!");
        } else {
            System.out.println("BigFASTA entry "+id+" not found!");
        }
    }

    public void saveToFile(Path outFile) {
        try {
            BufferedWriter clearWriter = new BufferedWriter(new FileWriter(outFile.toFile()));
            clearWriter.write("");
            clearWriter.close();

            BufferedWriter br = new BufferedWriter(new FileWriter(outFile.toFile(), true));
            br.write("#SR"+"\t"+filePath+"\n");
            br.write("#id\tstart\tend\n");
            for(BigFASTAEntry entry : entries) {
                br.write(entry.getId()+"\t"+entry.getPosition().getLong1()+"\t"+entry.getPosition().getLong2()+"\n");
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> keyList() {
        List<String> keys = new ArrayList<>();
        for(BigFASTAEntry entry : entries) {
            keys.add(entry.getId());
        }
        return keys;
    }

    public boolean containsEntry(String id) {
        return entries.stream().anyMatch(entry -> entry.getId().equals(id));
    }

    public void readFromFile(Path outFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(outFile.toFile()));
            String line;

            while((line = br.readLine()) != null) {
                if(line.startsWith("#")) {
                    if(line.startsWith("#SR"))
                        this.filePath = Paths.get(line.split("\t")[1]);
                }

                else {
                    String[] columns = line.split("\t");
                    entries.add(new BigFASTAEntry(columns[0], new LongPair(Long.parseLong(columns[1]), Long.parseLong(columns[2]))));
                }
            }

            br.close();

        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    public FASTA toFASTA() {
        FASTA fasta = new FASTA();
        for(BigFASTAEntry entry : entries) {
            fasta.addEntry(getFASTAEntry(entry.getId()));
        }
        return fasta;
    }

    public void sort(boolean ascending) {
        if(ascending) {
            entries.sort(new Comparator<BigFASTAEntry>() {
                @Override
                public int compare(BigFASTAEntry o1, BigFASTAEntry o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
        } else {
            entries.sort(new Comparator<BigFASTAEntry>() {
                @Override
                public int compare(BigFASTAEntry o1, BigFASTAEntry o2) {
                    return o2.getId().compareTo(o1.getId());
                }
            });
        }
    }

    public int size() {
        return entries.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(BigFASTAEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }
        return sb.toString();
    }
}
