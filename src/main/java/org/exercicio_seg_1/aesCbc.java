package org.exercicio_seg_1;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.exercicio_seg_1.dao.chaveAndIvRepository;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;


public class aesCbc {

    public chaveAndIvRepository chaveAndIvRepository = new chaveAndIvRepository();

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
        chaveAndIvRepository.salvarIvAndChaveToFile(iv, chaveSecreta);
        byte[] senhaCriptografadaBytes = cipher.doFinal(senha.getBytes());
        return Hex.toHexString(senhaCriptografadaBytes);
    }

}
