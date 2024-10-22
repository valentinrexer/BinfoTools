package org.example.src.structures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GFF3 {
    private List<String> comments;
    private List<GFF3Row> rows;

    public GFF3() {
        comments = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public void store(Path path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            String line;

            while ((line = br.readLine()) != null) {

            }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
