package org.exercicio_seg_1.criptografy;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import static org.exercicio_seg_1.dao.usersRepository.*;


public class aesCbc {

    public static aesEbc aesEbc = new aesEbc();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public byte[] geraIV() {
        //byte[] iv = new byte[16];
        //SecureRandom secureRandom = new SecureRandom();
        //secureRandom.nextBytes(iv);
        byte[] iv = Hex.decode("9f741fdb5d8845bdb48a94394e84f8a3");
        return iv;
    }

    public byte[] encrypter(String senha) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        SecretKeySpec chaveSecreta = keyderiver.derivarChaveComPBKDF2(senha);

        byte[] iv = geraIV();
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta, new IvParameterSpec(iv));
        byte[] senhaCriptografadaBytes = cipher.doFinal(senha.getBytes());

        saveIvAndChaveToFile(iv, senhaCriptografadaBytes);
        return senhaCriptografadaBytes;
    }


    public static String decrypter(byte[] senhaCriptografada, byte[] chaveSecretaBytes, byte[] ivBytes) throws Exception {
        try {
            SecretKeySpec chaveSecretaDescrypt = new SecretKeySpec(chaveSecretaBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, chaveSecretaDescrypt, new IvParameterSpec(ivBytes));

            byte[] passwordBytes = cipher.doFinal(senhaCriptografada);

            return new String(passwordBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Exception("Erro ao descriptografar a senha", e);
        }
    }


}
