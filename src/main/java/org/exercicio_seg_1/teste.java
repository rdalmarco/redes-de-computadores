package org.exercicio_seg_1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class teste {

    public static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        // Converter os bytes do hash em uma representação hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static void main(String[] args) {
        String input = "Hello, SHA-256!";
        try {
            String hash = hashString(input);
            System.out.println("Hash SHA-256 da string: " + hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo SHA-256 não encontrado.");
            e.printStackTrace();
        }
    }
}
