package br.ufg.inf.semaforo.agent;

import jade.core.Agent;

public class SemaforoAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119467267589466071L;
	
	@Override
	protected void setup() {
		System.out.println("Olá! Eu sou um agente, meu id é: " + getAID().getName());
	}

}
