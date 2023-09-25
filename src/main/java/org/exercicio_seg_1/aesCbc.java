package org.exercicio_seg_1;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

import static org.exercicio_seg_1.dao.usersRepository.salvarIvAndChaveToFile;


public class aesCbc {

    public static aesEbc aesEbc = new aesEbc();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public byte[] geraIV() {
        return Hex.decode("9f741fdb5d8845bdb48a94394e84f8a3");
    }

    public String encrypter(String senha) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        SecretKeySpec chaveSecreta = (SecretKeySpec) keyderiver.derivarChaveComPBKDF2(senha);
        byte[] iv = geraIV();
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta, new IvParameterSpec(iv));
        byte[] senhaCriptografadaBytes = cipher.doFinal(senha.getBytes());

        //Salvar com criptografia aesEBC no arquivo:
        String chaveString = Base64.getEncoder().encodeToString(chaveSecreta.getEncoded());
        String ivString = Base64.getEncoder().encodeToString(iv);
        salvarIvAndChaveToFile(chaveString, ivString);
        //salvarIvAndChaveToFile(aesEbc.encrypt(chaveSecreta, ivString), aesEbc.encrypt(chaveSecreta, chaveString));

        return Hex.toHexString(senhaCriptografadaBytes);
    }


    public static String decrypter(String senhaCriptografada, String chaveSecretaString, String ivString) throws Exception {
        try {
            byte[] chaveSecretaBytes = Base64.getDecoder().decode(chaveSecretaString);
            byte[] iv = Base64.getDecoder().decode(ivString);
            byte[] encryptedBytes = Base64.getDecoder().decode(senhaCriptografada);

            SecretKeySpec chaveSecreta = new SecretKeySpec(chaveSecretaBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, chaveSecreta, new IvParameterSpec(iv));

            byte[] decryptedPasswordBytes = cipher.doFinal(encryptedBytes, 16, encryptedBytes.length - 16);
            return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Exception("Erro ao descriptografar a senha", e);
        }
    }


}
