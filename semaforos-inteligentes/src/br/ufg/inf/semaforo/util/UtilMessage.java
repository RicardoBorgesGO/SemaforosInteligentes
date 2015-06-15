package br.ufg.inf.semaforo.util;

import java.io.IOException;
import java.io.Serializable;

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
	
	/**
	 * Envia mensagem com objeto serializado com a performativa passado por parametro
	 * @param content
	 * @param receiver
	 * @param agent
	 * @param aclMessage
	 */
	public static <T extends Serializable> void sendObjectMessage(T content, AID receiver, Agent agent, ACLMessage aclMessage) {
		try {
			ACLMessage msg = new ACLMessage(aclMessage.getPerformative());
			msg.setContentObject(content);
			msg.addReceiver(receiver);
			agent.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
