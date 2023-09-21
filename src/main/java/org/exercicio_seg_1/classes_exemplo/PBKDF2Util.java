package org.exercicio_seg_1.classes_exemplo;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.crypto.SecretKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * User: Carla
 * Date: 1 de setembro de 2015
 */

public class PBKDF2Util {

    /**
     * Gerar chave derivada da senha
     * @param key
     * @param salt
     * @param iterations
     * @return
     */
    public static String generateDerivedKey(
            String password, String salt, Integer iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 128);
        SecretKeyFactory pbkdf2 = null;
        String derivedPass = null;
        try {
            pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey sk = pbkdf2.generateSecret(spec);
            derivedPass = Hex.toHexString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivedPass;
    }

    /*Usado para gerar o salt  */
    public String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.toHexString(salt);
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        PBKDF2Util obj = new PBKDF2Util();

        String senha;
        String salt;
        int it = 10000;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a senha: ");
        senha = input.nextLine();

        //senha = "123456789";
        salt = obj.getSalt();

        System.out.println("Senha original = " + senha);
        System.out.println("Sal gerado = " + salt);
        System.out.println("Numero de iteracoes = " + it);

        String chaveDerivada = generateDerivedKey(senha, salt, it);

        System.out.println("Chave derivada da senha = " + chaveDerivada );

    }


}
