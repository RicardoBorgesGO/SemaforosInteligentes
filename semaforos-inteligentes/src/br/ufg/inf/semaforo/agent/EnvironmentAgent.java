package br.ufg.inf.semaforo.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.environment.Car;
import br.ufg.inf.semaforo.environment.Street;
import br.ufg.inf.semaforo.util.UtilRandom;

public class EnvironmentAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -104356731541589090L;

	private List<Street> streets;
	
	/**
	 * Quantidade minima de carros
	 */
	private static final Integer INIT_COUNT_CAR = 3;
	
	/**
	 * Quantidade maxima de carros por periodo
	 */
	private static final Integer BOUND_COUNT_CAR = 10;
	
	@Override
	@SuppressWarnings("serial")
	protected void setup() {
		System.out.println("Olá! Eu sou um agente Ambiente, meu id é: " + getAID().getName());
		
		//TODO Tentar fazer tudo sincrono 
		
		//Criando ruas
		streets = new ArrayList<Street>();
		streets.add(new Street(2, EnumTrackDirection.SENTIDO_DUPLO));
		streets.add(new Street(2, EnumTrackDirection.SENTIDO_DUPLO));
		streets.add(new Street(2, EnumTrackDirection.SENTIDO_DUPLO));
		streets.add(new Street(2, EnumTrackDirection.SENTIDO_DUPLO));
		
		//Criando comportamento
		addBehaviour(new TickerBehaviour(this, 5000) {
			
			@Override
			protected void onTick() {
				for (Street street : streets) {
					int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR, BOUND_COUNT_CAR);
					
					for (int i = 0; i < quantidadeDeCarros; i++) {
						street.addCar(new Car());
					}
				}
				//TODO Continuar
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID("Peter", AID.ISLOCALNAME));
				msg.setContent("Today it’s raining");
				send(msg); 
			}
		});
	}
	
	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		super.takeDown();
	}

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}

}
