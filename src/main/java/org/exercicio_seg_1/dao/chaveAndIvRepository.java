package org.exercicio_seg_1.dao;

import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class chaveAndIvRepository {

    static String outputChaveAndIv = "ChaveAndIv.txt";
    static String delimiter = "--------------------------------------------------";

    public void salvarIvAndChaveToFile(byte[] iv, SecretKey chaveSecreta) throws IOException {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputChaveAndIv))) {
            save.write(iv.toString());
            save.newLine();
            save.write(chaveSecreta.toString());
            save.newLine();
            save.write(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

