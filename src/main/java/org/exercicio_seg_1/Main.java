package org.exercicio_seg_1;

import org.exercicio_seg_1.dao.chaveAndIvRepository;
import org.exercicio_seg_1.dao.usersRepository;
import org.exercicio_seg_1.model.userModel;

import java.security.Provider;
import java.security.Security;
import java.util.Scanner;

public class Main {

    public usersRepository userRepository;

    public static sha256 sha256 = new sha256();
    public static aesCbc aesCbc = new aesCbc();
    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o seu usuário: ");
        String usuario = scanner.next();

        System.out.print("Digite a sua senha: ");
        String senha = scanner.next();

        userModel user = new userModel(usuario,senha);

        String usernameCriptografado = sha256.encrypt(user);

        String passwordCriptografada = aesCbc.encrypter(senha);

        usersRepository.saveUsernameAndPasswordToFile(usernameCriptografado, passwordCriptografada);
    }
}