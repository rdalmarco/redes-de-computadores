package org.exercicio_seg_1;

import org.exercicio_seg_1.model.userModel;

import java.util.Scanner;

public class Main {

    public static sha256 sha256 = new sha256();
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o seu usuário: ");
        String usuario = scanner.next();

        System.out.print("Digite a sua senha: ");
        String senha = scanner.next();

        userModel user = new userModel(usuario,senha);

        sha256.encrypt(user);

    }
}