package com.mygdx.dungeonsagents.ai;

import com.mygdx.dungeonsagents.Utils;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Mestre extends Agent {
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                int count = 0;

                while (count < 6) {
                    String jogador = Utils.getAgent(count);
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID(jogador, AID.ISLOCALNAME));
                    msg.setContent("SeuTurno");
                    send(msg);

                    ACLMessage confirmationMsg = blockingReceive();
                    if (confirmationMsg != null && confirmationMsg.getContent().equalsIgnoreCase("AcaoConcluida")) {
                        count++;
                    }
                }
            }
        });
    }
}
