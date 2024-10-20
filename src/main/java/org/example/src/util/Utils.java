package org.example.src.util;

import org.example.src.structures.LongPair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class Utils {

    public static String readBytesFromPosToPos(Path filePath, long start, long end, boolean strip){
        try (RandomAccessFile pointer = new RandomAccessFile(filePath.toFile(), "r")){
            byte[] bytes = new byte[(int)(end-start)];

            pointer.seek(start);
            pointer.readFully(bytes);

            if(strip){
                String s = new String(bytes);
                s = s.replace("\n", "").replace("\r", "").replace("\t", "");
                return s;
            } else return new String(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String readBytesFromPosToPos(Path filePath, LongPair interval, boolean strip){
        return readBytesFromPosToPos(filePath, interval.getLong1(), interval.getLong2(), strip);
    }
}
