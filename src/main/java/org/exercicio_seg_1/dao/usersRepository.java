package org.exercicio_seg_1.dao;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class usersRepository {

    static String outputUserAndPassword = "UserAndPassword.txt";

    public static void saveHashToFile(byte[] hash) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(outputUserAndPassword);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            bos.write(hash);
        }
    }

}
