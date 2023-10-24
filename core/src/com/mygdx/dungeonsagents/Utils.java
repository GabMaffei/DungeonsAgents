package com.mygdx.dungeonsagents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static int Dado(int tamanho) {
        Random random = new Random();
        return random.nextInt(tamanho);
    }

    public static int D20() {
        Random random = new Random();
        return random.nextInt(20);
    }

    public static int D10() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public static int D6() {
        Random random = new Random();
        return random.nextInt(6);
    }

    public static float calcularDado(float energia) {
        return Math.round(((float) energia / 100 + 1) * D20());
    }

    public static float calcularDefesa(float defesa) {
        return Math.round(((float) defesa / 100 + 1) * D6());
    }


    public static float receberAtaque(float vidaAtual, float energia, float defesa, int tipo) {
        float def = calcularDefesa(defesa);
        float dano = calcularDado(energia);

        if (def >= dano) {
            ExibeResultadoAtaque("O ataque foi defendido");
            return vidaAtual;
        } else {
            float danoCausado = dano - def;
            if (tipo == 2) {
                danoCausado = danoCausado / 3;
            }

            vidaAtual = Math.round(vidaAtual - danoCausado);

            ExibeResultadoAtaque("O ataque acertou e causou " + danoCausado + " de dano");
            return vidaAtual;
        }
    }

    public static String ExibeResultadoAtaque(String mensagem) {
        // Implementar chamada da interface gráfica
        System.out.println(mensagem);
        return mensagem;
    }

    public static void exibirAcoes(){
        System.out.println("Qual acao deseja fazer?");
        System.out.println("1 - Ataque");
        System.out.println("2 - Ataque em Area");
    }

    public static void exibirAlvos() {
        System.out.println("Qual o alvo do seu ataque?");
        System.out.println("1 - Inimigo 1");
        System.out.println("2 - Inimigo 2");
        System.out.println("3 - Inimigo 3");
    }

    public static int scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String getNomeAlvo(int alvo) {
        if (alvo == 1) {
            return "OrcBerserk";
        } else if (alvo == 2) {
            return "OrcWarrior";
        } else {
            return "OrcShaman";
        }
    }

    public static String getAgent(int number) {
        if (number == 0){
            return "Arqueiro";
        } else if (number == 1) {
            return "OrcBerserk";
        } else if (number == 2) {
            return "Guerreiro";
        } else if (number == 3) {
            return "OrcWarrior";
        } else if (number == 4) {
            return "Mago";
        } else {
            return "OrcShaman";
        }
    }
}
