package org.exercicio_seg_1.criptografy;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.exercicio_seg_1.dao.usersRepository;
import org.exercicio_seg_1.model.ivAndKey;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;


public class aesEcb {

    private static Cipher cipher;

    static {
        Security.addProvider(new BouncyCastleProvider());
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encrypt(String usernameCriptografado) throws Exception {
        byte[] chaveSecretaBytes = new byte[16];
        byte[] usernameBytes = usernameCriptografado.getBytes();
        System.arraycopy(usernameBytes, 0, chaveSecretaBytes, 0, Math.min(usernameBytes.length, 16));

        SecretKeySpec chaveSecreta = new SecretKeySpec(chaveSecretaBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta);

        File arquivo = new File("KeyAndIv_" + usernameCriptografado + ".txt");
        long tamanhoArquivo = arquivo.length();

        FileInputStream arquivoInput = new FileInputStream("KeyAndIv_" + usernameCriptografado + ".txt");
        byte[] dados = new byte[(int) tamanhoArquivo];
        arquivoInput.read(dados);
        arquivoInput.close();

        byte[] dadosCifrados = cipher.doFinal(dados);

        FileOutputStream arquivoOutput = new FileOutputStream("KeyAndIv_" + usernameCriptografado + ".txt");
        arquivoOutput.write(dadosCifrados);
        arquivoOutput.close();

        System.out.println("Arquivo cifrado com sucesso utilizando ECB");
    }


    public static void decrypt(String usernameCriptografado) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] chaveSecretaBytes = new byte[16];
        byte[] usernameBytes = usernameCriptografado.getBytes();
        System.arraycopy(usernameBytes, 0, chaveSecretaBytes, 0, Math.min(usernameBytes.length, 16));

        SecretKeySpec chaveSecreta = new SecretKeySpec(chaveSecretaBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, chaveSecreta);

        File arquivo = new File("KeyAndIv_" + usernameCriptografado + ".txt");
        long tamanhoArquivo = arquivo.length();

        FileInputStream arquivoInput = new FileInputStream("KeyAndIv_" + usernameCriptografado + ".txt");
        byte[] dadosCifrados = new byte[(int) tamanhoArquivo];
        arquivoInput.read(dadosCifrados);
        arquivoInput.close();

        byte[] dadosDecifrados = cipher.doFinal(dadosCifrados);

        FileOutputStream arquivoOutput = new FileOutputStream("KeyAndIv_" + usernameCriptografado + "_temp.txt");
        arquivoOutput.write(dadosDecifrados);
        arquivoOutput.close();

        System.out.println("Arquivo decifrado com sucesso utilizando ECB");
    }
    }


