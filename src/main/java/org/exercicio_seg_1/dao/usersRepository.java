package org.exercicio_seg_1.dao;


import org.exercicio_seg_1.aesCbc;
import org.exercicio_seg_1.aesEbc;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class usersRepository {

    static String outputUserAndPassword = "UserAndPassword.txt";

    static String outputChaveAndIv = "ChaveAndIv.txt";
    static String delimiter = "--------------------------------------------------";

    public static void saveUsernameAndPasswordToFile(String username, String senha) throws Exception {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputUserAndPassword, true))) {
            save.newLine();
            save.write("Usuario: " + username);
            save.newLine();
            save.write("Senha: " + senha);
            save.newLine();
            save.write(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarIvAndChaveToFile(String iv, String chaveSecreta) throws IOException {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputChaveAndIv, true))) {

            save.newLine();
            save.write("IV: " + iv);
            save.newLine();
            save.write("KEY: " + chaveSecreta);
            save.newLine();
            save.write(delimiter);
        }
    }


    public static void verificaCredenciais(String username, String password) {
        try {
            String iv = null;
            String chaveSecreta = null;
            String usernameCriptografado = null;
            String senhaCriptografada = null;

            BufferedReader reader = new BufferedReader(new FileReader("chaveAndIv.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("IV: ")) {
                    iv = line.substring(4); //
                } else if (line.startsWith("KEY: ")) {
                    chaveSecreta = line.substring(5); //
                }
            }

            reader.close();

            System.out.println("IV: " + iv);
            System.out.println("KEY: " + chaveSecreta);

            reader = new BufferedReader(new FileReader("UserAndPassword.txt"));

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Senha: ")) { // Verificar a linha da senha criptografada
                    senhaCriptografada = line.substring(7);
                    break; // Parar o loop após encontrar a senha
                }
            }

            reader.close();

            System.out.println("Senha Criptografada: " + senhaCriptografada);

            System.out.println(aesCbc.decrypter(senhaCriptografada, chaveSecreta, iv));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }



