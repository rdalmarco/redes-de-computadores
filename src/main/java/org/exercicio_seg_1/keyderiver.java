package org.exercicio_seg_1;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class keyderiver {

    public static byte[] derivarChaveComPBKDF2(String senha, byte[] salt) throws Exception {
        KeySpec espec = new PBEKeySpec(senha.toCharArray(), salt, 1000, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey chaveSecreta = factory.generateSecret(espec);
        return chaveSecreta.getEncoded();
    }

    //Gerar salt randômico
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
