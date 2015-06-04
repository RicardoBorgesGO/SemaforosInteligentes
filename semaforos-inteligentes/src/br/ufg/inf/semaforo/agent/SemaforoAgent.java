package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SemaforoAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119467267589466071L;
	
	public static Integer COUNT_SEMAFOROS = 0;
	
	@SuppressWarnings("serial")
	@Override
	protected void setup() {
		System.out.println("Olá! Eu sou um agente Semáforo, meu id é: " + getAID().getName());
		getAID().setLocalName("Semaforo-" + COUNT_SEMAFOROS++);
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				ACLMessage message = myAgent.receive(mt);
				
				if (message != null) {
					// ...
				} else {
					//TODO Testar sem o block();
					block();
				}
			}
		});
	}

}
