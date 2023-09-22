package org.exercicio_seg_1;

import org.exercicio_seg_1.dao.usersRepository;
import org.exercicio_seg_1.model.userModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256 {

    usersRepository usersRepository;

    public void encrypt(userModel user) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] inputBytes = user.getUsuario().getBytes(StandardCharsets.UTF_8);

        byte[] hash = digest.digest(inputBytes);

        usersRepository.saveHashToFile(hash);
    }
}
