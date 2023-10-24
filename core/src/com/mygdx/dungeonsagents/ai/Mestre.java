package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Utils;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mestre extends Agent {
    private int jogadorAtual = 0;
    private boolean aguardandoConfirmacao = false;

    public void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                if (!aguardandoConfirmacao) {
                    String jogador = Utils.getAgent(jogadorAtual);
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID(jogador, AID.ISLOCALNAME));
                    msg.setContent("SeuTurno");
                    send(msg);

                    aguardandoConfirmacao = true;
                } else {
                    ACLMessage mensagem = receive();
                    if (mensagem != null && mensagem.getContent().equals("JogadaConcluida")) {
                        aguardandoConfirmacao = false;
                        jogadorAtual++;
                        if (jogadorAtual >= 6) {
                            jogadorAtual = 0;
                        }
                    }
                }
            }
        });
    }
}
