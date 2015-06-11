package br.ufg.inf.semaforo.util;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class UtilMessage {

	public static void sendInformMessage(String content, AID receiver, Agent agent) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(content);
		msg.addReceiver(receiver);
		agent.send(msg);
	}
}
