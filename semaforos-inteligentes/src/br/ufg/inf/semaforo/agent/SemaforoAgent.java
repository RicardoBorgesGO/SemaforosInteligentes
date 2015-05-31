package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class SemaforoAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119467267589466071L;
	
	@Override
	protected void setup() {
		System.out.println("Ol�! Eu sou um agente, meu id �: " + getAID().getName());
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			
			@Override
			protected void onTick() {
				System.out.println("Opa!");
			}
		});
	}

}
