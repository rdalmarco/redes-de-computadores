package org.exercicio_seg_1;

import org.exercicio_seg_1.criptografy.aesCbc;
import org.exercicio_seg_1.criptografy.sha256;
import org.exercicio_seg_1.dao.usersRepository;
import org.exercicio_seg_1.model.userModel;

import java.util.Scanner;

public class Main {

    public usersRepository userRepository;

    public static org.exercicio_seg_1.criptografy.sha256 sha256 = new sha256();
    public static org.exercicio_seg_1.criptografy.aesCbc aesCbc = new aesCbc();

    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o seu usuário: ");
        String usuario = scanner.next();

        System.out.print("Digite a sua senha: ");
        String senha = scanner.next();

        userModel user = new userModel(usuario,senha);

        String usernameCriptografado = sha256.encrypt(user.getUsuario());

        usersRepository.saveUsernameAndPasswordToFile(usernameCriptografado, aesCbc.encrypter(senha));
        usersRepository.verificaCredenciais(usuario, senha);
    }
}