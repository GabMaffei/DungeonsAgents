package com.mygdx.dungeonsagents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static float calcularDado(int energia) {
        return Math.round(((float) energia / 100 + 1) * D20());
    }

    public static float calcularDefesa(int defesa) {
        return Math.round(((float) defesa / 100 + 1) * D6());
    }

    public static int receberAtaque(int vidaAtual, int energia, int defesa, String tipoAtaque, String nome) {
        return receberAtaque(vidaAtual, energia, defesa, tipoAtaque, nome, false);
    }

    public static int receberAtaque(int vidaAtual, int energia, int defesa, String tipoAtaque, String nome, boolean area) {
        float def = calcularDefesa(defesa);
        float dano = calcularDado(energia);

        if (def >= dano) {
            ExibeResultadoAtaque("O ataque foi defendido");
            return vidaAtual;
        } else {
            float danoCausado = dano - def;
            if (area) {
                danoCausado = danoCausado / 3;
            }

            vidaAtual = Math.round(vidaAtual - danoCausado);

            ExibeResultadoAtaque("O ataque acertou e causou " + danoCausado + " de dano");
            return vidaAtual;
        }
    }

    public static String ExibeResultadoAtaque(String mensagem) {
        // Implementar chamada da interface gráfica
        return mensagem;
    }

    public static float[] calcularIniciativa() {
        float[] iniciativas = new float[6];
        int count = 0;

        for (count = 0; count < 6; count++) {
            if (count == 0 || count == 3) {
                iniciativas[count] = 0.9f * D20();
            } else if (count == 1 || count == 4) {
                iniciativas[count] = 0.8f * D20();
            } else {
                iniciativas[count] = 1.0f * D20();
            }
        }

        return iniciativas;
    }
}
