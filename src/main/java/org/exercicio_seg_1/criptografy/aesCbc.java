package org.exercicio_seg_1.criptografy;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

import static org.exercicio_seg_1.dao.usersRepository.*;


public class aesCbc {

    public static aesEcb aesEbc = new aesEcb();

    private static Cipher cipher;

    static {
        Security.addProvider(new BouncyCastleProvider());
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] geraIV() {
        byte[] iv = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }

    public byte[] encrypter(String senha, String usernameCriptografado) throws Exception {
        SecretKeySpec chaveSecreta = keyderiver.derivarChaveComPBKDF2(senha);

        byte[] iv = geraIV();
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta, new IvParameterSpec(iv));
        byte[] senhaCriptografadaBytes = cipher.doFinal(senha.getBytes());

        saveIvAndChaveToFile(iv, chaveSecreta.getEncoded(), usernameCriptografado);
        System.out.println("Dados cifrados com sucesso utilizando CBC");

        //Chamada para encriptar com ECB arquivo já criado depois da criptografia CBC
        aesEbc.encrypt(usernameCriptografado);

        return senhaCriptografadaBytes;
    }


    public static String decrypter(byte[] senhaCriptografada, byte[] chaveSecretaBytes, byte[] ivBytes) throws Exception {
        try {
            SecretKeySpec chaveSecretaDescrypt = new SecretKeySpec(chaveSecretaBytes, "AES");

            cipher.init(Cipher.DECRYPT_MODE, chaveSecretaDescrypt, new IvParameterSpec(ivBytes));

            byte[] passwordBytes = cipher.doFinal(senhaCriptografada);

            System.out.println("Senha decifrada com sucesso utilizando CBC");
            return new String(passwordBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Exception("Erro ao descriptografar a senha", e);
        }
    }


}
