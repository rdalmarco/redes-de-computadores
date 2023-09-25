package org.exercicio_seg_1;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class aesEbc {

    File inputFile = new File("ChaveAndIv.txt");
    File outputFile = new File("encrypted_ChaveAndIv.txt");

    public String encrypt(SecretKeySpec chaveSecreta, String valor) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta);
        byte[] encryptedBytes = cipher.doFinal(valor.getBytes());

        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

        return encryptedString;
    }


}
