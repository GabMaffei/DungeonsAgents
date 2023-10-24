package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
import com.mygdx.dungeonsagents.Utils;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Orc_Shaman extends Entity {
    public Orc_Shaman() {
        super(false, 2, 2, 1280, 720, 40, 100, 20);
    }

    public Orc_Shaman(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense) {
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
                        int acao = Utils.Dado(2);
                        System.out.println(acao);
                        if (acao == 1) {
                            String nomeAlvo = Utils.getNomeAlvo(Utils.Dado(3));
                            enviaMsg(nomeAlvo, "Ataque", "Energia", "" + energy);
                        } else {
                            ataqueEmArea();
                        }
                    } else {
                        float energiaInimigo = Float.parseFloat(msg.getUserDefinedParameter("Energia"));
                        if (content.equalsIgnoreCase("Ataque")) {
                            healthPoints = Utils.receberAtaque(healthPoints, energiaInimigo, defense, 1);
                        } else {
                            healthPoints = Utils.receberAtaque(healthPoints, energiaInimigo, defense, 2);
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
        sendMsg.addReceiver(new AID("Arqueiro", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("Guerreiro", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("Mago", AID.ISLOCALNAME));
        sendMsg.setContent("AtaqueEmArea");
        sendMsg.addUserDefinedParameter("Energia", "" + this.energy);
        send(sendMsg);
    }
}
