package org.exercicio_seg_1.criptografy;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class keyderiver {

    public static SecretKeySpec derivarChaveComPBKDF2(String senha) throws Exception {
        try {
            byte[] salt = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
            //byte[] salt = generateSalt();
            SecretKeyFactory pbkdf2 = null;
            PBEKeySpec spec  = new PBEKeySpec(senha.toCharArray(), salt, 1000, 128);
            pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey sk = pbkdf2.generateSecret(spec);
            byte[] chaveBytes = sk.getEncoded();
            return new SecretKeySpec(chaveBytes, "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new Exception("Erro ao derivar a chave com PBKDF2", e);
        }
    }

    //Gerar salt randômico
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}