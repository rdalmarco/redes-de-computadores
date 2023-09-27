package org.exercicio_seg_1.dao;


import org.exercicio_seg_1.criptografy.aesCbc;
import org.exercicio_seg_1.criptografy.sha256;

import java.io.*;

public class usersRepository {

    static String outputUserAndPassword = "UserAndPassword.txt";

    static String outputChaveAndIv = "ChaveAndIv.txt";
    static String delimiter = "--------------------------------------------------";

    public static void saveUsernameAndPasswordToFile(String username, byte[] senha) throws Exception {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputUserAndPassword, true))) {
            save.newLine();
            save.write("Usuario: " + username);
            save.newLine();
            save.write("Senha: " + byteArrayToHexString(senha));
            save.newLine();
            save.write(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveIvAndChaveToFile(byte[] iv, byte[] chaveSecreta) throws IOException {
        try (BufferedWriter save = new BufferedWriter(new FileWriter(outputChaveAndIv, true))) {

            save.newLine();
            save.write("IV: " + byteArrayToHexString(iv));
            save.newLine();
            save.write("KEY: " + byteArrayToHexString(chaveSecreta));
            save.newLine();
            save.write(delimiter);
        }
    }

    public static boolean verificaCredenciais(String username, String password) {
        try {
            byte[] iv = null;
            byte[] chaveSecreta = null;
            String usernameCriptografado = null;
            byte[] senhaCriptografada = null;

            BufferedReader readerUser = new BufferedReader(new FileReader("UserAndPassword.txt"));
            String line;

            while ((line = readerUser.readLine()) != null) {
                if (line.startsWith("Usuario: ")) {
                    usernameCriptografado = line.substring(9);
                } else if
                (line.startsWith("Senha: ")) {
                    senhaCriptografada = hexStringToByteArray(line.substring(7));
                }

            }
            //System.out.println("Usuario: " + usernameCriptografado);
            //System.out.println("Senha: " + byteArrayToHexString(senhaCriptografada));
            readerUser.close();

            String userAutenticar = sha256.encrypt(username);
            if (userAutenticar.equals(usernameCriptografado)) {
                //System.out.println("Usuario encontrado");

                BufferedReader readerIvAndKey = new BufferedReader(new FileReader("chaveAndIv.txt"));
                line = null;

                while ((line = readerIvAndKey.readLine()) != null) {
                    if (line.startsWith("IV: ")) {
                        iv = hexStringToByteArray(line.substring(4)); //
                    } else if (line.startsWith("KEY: ")) {
                        chaveSecreta = hexStringToByteArray(line.substring(5)); //
                    }
                }

                readerIvAndKey.close();

                //System.out.println("IV: " + byteArrayToHexString(iv));
                //System.out.println("KEY: " + byteArrayToHexString(chaveSecreta));

                if (aesCbc.decrypter(senhaCriptografada, chaveSecreta, iv).equals(password)) {
                    System.out.println("Usuario logado");
                    return true;
                } else {
                    System.out.println("Senha incorreta");
                    return false;
                }
            } else {
                System.out.println("Usuario nao encontrado");
                return false;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] valor = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            valor[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return valor;
    }
}