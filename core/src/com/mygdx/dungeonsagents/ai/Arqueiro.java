package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
import com.mygdx.dungeonsagents.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Arqueiro extends Entity {
    private BlockingQueue<Integer> userInputQueue = new LinkedBlockingQueue<>();
    public Arqueiro() {
        super(true, 0, 0, 1280, 720, 50, 90, 25);
    }

    public Arqueiro(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense);
    }

    @Override
    protected void setup() {
        float energy = this.energy;
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equalsIgnoreCase("SeuTurno")) {
                        Utils.exibirAcoes();
                        try {
                            int acao = userInputQueue.take();
                            if (acao == 1) {
                                Utils.exibirAlvos();
                                int alvo = userInputQueue.take();
                                String nomeAlvo = Utils.getNomeAlvo(alvo);
                                enviaMsg(nomeAlvo, "Ataque", "Energia", "" + energy);
                                enviarMsgConfirmacao();
                            } else {
                                ataqueEmArea();
                                enviarMsgConfirmacao();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        float energiaInimigo = Float.parseFloat(msg.getUserDefinedParameter("Energia"));
                        if (content.equalsIgnoreCase("Ataque")){
                            healthPoints = Utils.receberAtaque(healthPoints, energiaInimigo, defense, 1);
                            System.out.println(healthPoints);
                        } else {
                            healthPoints = Utils.receberAtaque(healthPoints, energiaInimigo, defense, 2);
                            System.out.println(healthPoints);
                        }
                    }
                }
            }
        });
    }

    public void enviaMsg(String destino, String conteudo, String key1, String value1) {
        ACLMessage sendMsg = new ACLMessage(ACLMessage.INFORM);
        sendMsg.addReceiver(new AID(destino, AID.ISLOCALNAME));
        sendMsg.setContent(conteudo);
        sendMsg.addUserDefinedParameter(key1, value1);
        send(sendMsg);
    }

    public void ataqueEmArea() {
        ACLMessage sendMsg = new ACLMessage(ACLMessage.INFORM);
        sendMsg.addReceiver(new AID("OrcBerserk", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("OrcWarrior", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("OrcShaman", AID.ISLOCALNAME));
        sendMsg.setContent("AtaqueEmArea");
        sendMsg.addUserDefinedParameter("Energia", "" + this.energy);
        send(sendMsg);
    }

    public void setUserInput(int input) {
        try {
            userInputQueue.put(input);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enviarMsgConfirmacao() {
        ACLMessage confirmationMsg = new ACLMessage(ACLMessage.INFORM);
        confirmationMsg.addReceiver(new AID("Mestre", AID.ISLOCALNAME));
        confirmationMsg.setContent("AcaoConcluida");
        send(confirmationMsg);
    }
}
