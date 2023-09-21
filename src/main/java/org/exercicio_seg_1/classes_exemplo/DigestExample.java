package org.exercicio_seg_1.classes_exemplo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import java.security.Security;
import java.security.MessageDigest;

/**
 * A simple example of using a MessageDigest.
 */
public class DigestExample
{
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] computeDigest(String algorithm, byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digest.digest(data);
    }

    public static void main(String[] args)
        throws Exception
    {
        System.out.println(
            Hex.toHexString(
                computeDigest("SHA-256", Strings.toByteArray("Hello World!"))));
    }
}
