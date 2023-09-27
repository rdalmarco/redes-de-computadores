package org.exercicio_seg_1;

import org.exercicio_seg_1.criptografy.aesCbc;
import org.exercicio_seg_1.criptografy.sha256;
import org.exercicio_seg_1.dao.usersRepository;
import org.exercicio_seg_1.model.userModel;

import java.util.Scanner;

public class Main {
    public static org.exercicio_seg_1.criptografy.sha256 sha256 = new sha256();
    public static org.exercicio_seg_1.criptografy.aesCbc aesCbc = new aesCbc();

    private static int option;

    private static String usuario;
    private static String senha;


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Cadastro, 2 - Login, 3 - Encerrar");

        try {
           option = scanner.nextInt();
        } catch(Exception e) {
            System.out.println("Digite uma opção válida, reiniciando");
        }


        if (option == 1) {
            System.out.print("Digite um usuário: ");
            usuario = scanner.next();

            System.out.print("Digite uma senha: ");
            senha = scanner.next();

            userModel user = new userModel(usuario, senha);

            String usernameCriptografado = sha256.encrypt(user.getUsuario());

            usersRepository.saveUsernameAndPasswordToFile(usernameCriptografado, aesCbc.encrypter(senha));
        } else
            if (option == 2) {
                System.out.print("Digite o seu usuário: ");
                usuario = scanner.next();

                System.out.print("Digite a sua senha: ");
                senha = scanner.next();
                usersRepository.verificaCredenciais(usuario, senha);
            }


    }
}