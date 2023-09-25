package org.exercicio_seg_1.dao;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class usersRepository {

    static String outputUserAndPassword = "UserAndPassword.txt";

    static String delimiter = "--------------------------------------------------";

    public static void saveUsernameAndPasswordToFile(String username, String senha) throws Exception {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputUserAndPassword))) {
            save.write(username);
            save.newLine();
            save.write(senha);
            save.newLine();
            save.write(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

