package org.exercicio_seg_1.dao;


import org.exercicio_seg_1.criptografy.aesCbc;
import org.exercicio_seg_1.criptografy.aesEcb;
import org.exercicio_seg_1.criptografy.sha256;
import org.exercicio_seg_1.model.ivAndKey;

import java.io.*;

public class usersRepository {


    public static void saveUsernameAndPasswordToFile(String usernameCriptografado, byte[] senha) throws Exception {
        try (BufferedWriter save = new BufferedWriter(new FileWriter("UserAndPass_" + usernameCriptografado + ".txt", true))) {
            save.write("Usuario: " + usernameCriptografado);
            save.newLine();
            save.write("Senha: " + byteArrayToHexString(senha));
            save.newLine();
            System.out.println("Usuário cadastrado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveIvAndChaveToFile(byte[] iv, byte[] chaveSecreta, String usernameCriptografado) throws IOException {
        try (BufferedWriter save = new BufferedWriter(new FileWriter("KeyAndIv_" + usernameCriptografado + ".txt", true))) {
            save.write("IV: " + byteArrayToHexString(iv));
            save.newLine();
            save.write("KEY: " + byteArrayToHexString(chaveSecreta));
            save.newLine();
        }
    }

    public static boolean verificaCredenciais(String username, String password) {
        try {
            byte[] iv = null;
            byte[] chaveSecreta = null;
            String usernameCriptografado = null;
            byte[] senhaCriptografada = null;

            usernameCriptografado = sha256.encrypt(username);

            BufferedReader readerUser = new BufferedReader(new FileReader("UserAndPass_" + usernameCriptografado + ".txt"));
            String line;

            if (readerUser != null) {

                while ((line = readerUser.readLine()) != null) {
                    if (line.startsWith("Senha: ")) {
                        senhaCriptografada = hexStringToByteArray(line.substring(7));
                    }
                }
                readerUser.close();

                aesEcb.decrypt(usernameCriptografado);

                BufferedReader readerIvAndKey = new BufferedReader(new FileReader("KeyAndIv_" + usernameCriptografado + "_temp.txt"));
                line = null;

                while ((line = readerIvAndKey.readLine()) != null) {
                    if (line.startsWith("IV: ")) {
                        iv = hexStringToByteArray(line.substring(4)); //
                    } else if (line.startsWith("KEY: ")) {
                        chaveSecreta = hexStringToByteArray(line.substring(5)); //
                    }
                }

                readerIvAndKey.close();

                if (aesCbc.decrypter(senhaCriptografada, chaveSecreta, iv).equals(password)) {
                    System.out.println("Usuario logado");
                    File readerIvAndKey_temp = new File("KeyAndIv_" + usernameCriptografado + "_temp.txt");
                    readerIvAndKey_temp.delete();
                    return true;
                } else {
                    System.out.println("Senha incorreta");
                    return false;
                }
            } else {
                return false;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Usuário não encontrado");
            return false;
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