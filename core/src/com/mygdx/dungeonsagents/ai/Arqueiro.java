package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Entity;
import com.mygdx.dungeonsagents.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

public class Arqueiro extends Entity {
    public Arqueiro() {
        super(true, 0, 0, 1280, 720, 50, 90, 25, 0, 0);
    }

    public Arqueiro(boolean ally, int placement, int fightingClass, float viewportWidth, float viewportHeight, float healthPoints, float energy, float defense, int turno, float iniciativa) {
        super(ally, placement, fightingClass, viewportWidth, viewportHeight, healthPoints, energy, defense, turno, iniciativa);
    }

    @Override
    protected void setup() {
        float energy = this.energy;
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String content = msg.getContent();
                    int energiaInimigo = Integer.parseInt(msg.getUserDefinedParameter("Energia"));
                    String tipoAtaque = msg.getUserDefinedParameter("TipoAtaque");
                    if (content.equalsIgnoreCase("Ataque")) {
                        enviaMsg(msg.getUserDefinedParameter("NomeInimigo"), "Ataque", "Energia", "" + energy, "TipoAtaque", "Flechada");
                    } else if (content.equalsIgnoreCase("Especial")) {

                    } else if (content.equalsIgnoreCase("AtaqueEmArea")) {
                        ataqueEmArea();
                    } else if (content.equalsIgnoreCase("AtaqueInimigo")) {
                        healthPoints = Utils.receberAtaque(healthPoints,energiaInimigo,defense,tipoAtaque,getLocalName());
                    } else if (content.equalsIgnoreCase("AtaqueInimigoEmArea")) {
                        healthPoints = Utils.receberAtaque(healthPoints,energiaInimigo,defense,tipoAtaque, getLocalName(),true);
                    } else if (content.equalsIgnoreCase("EspecialInimigo")) {

                    }
                }
            }
        });
    }

    public void enviaMsg(String destino, String conteudo, String key1, String value1, String key2, String value2) {
        ACLMessage sendMsg = new ACLMessage(ACLMessage.INFORM);
        sendMsg.addReceiver(new AID(destino, AID.ISLOCALNAME));
        sendMsg.setContent(conteudo);
        sendMsg.addUserDefinedParameter(key1, value1);
        sendMsg.addUserDefinedParameter(key2, value2);
        send(sendMsg);
    }

    public void ataqueEmArea() {
        ACLMessage sendMsg = new ACLMessage(ACLMessage.INFORM);
        sendMsg.addReceiver(new AID("enemy1", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("enemy2", AID.ISLOCALNAME));
        sendMsg.addReceiver(new AID("enemy3", AID.ISLOCALNAME));
        sendMsg.setContent("AtaqueEmArea");
        sendMsg.addUserDefinedParameter("Energia", "" + this.energy);
        sendMsg.addUserDefinedParameter("TipoAtaque", "Chuva de Flechas");
        send(sendMsg);
    }
}
