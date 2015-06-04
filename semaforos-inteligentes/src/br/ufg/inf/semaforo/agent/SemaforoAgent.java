package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class SemaforoAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119467267589466071L;
	
	public static Integer COUNT_SEMAFOROS = 0;
	
	@Override
	protected void setup() {
		System.out.println("Olá! Eu sou um agente Semáforo, meu id é: " + getAID().getName());
		getAID().setLocalName("Semaforo-" + COUNT_SEMAFOROS++);
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				
			}
		});
	}

}
