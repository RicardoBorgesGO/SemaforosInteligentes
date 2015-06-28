package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.environment.Car;
import br.ufg.inf.semaforo.environment.Sensor;
import br.ufg.inf.semaforo.environment.Street;
import br.ufg.inf.semaforo.util.UtilAgent;
import br.ufg.inf.semaforo.util.UtilRandom;

public class TrafficLightAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446136644934480233L;
	
	private Sensor sensor;
	
	public static String TYPE_AGENT = "trafficLightAgent";
	
	private static String AGENT_NAME = "trafficLight";
	
	private static Integer COUNT_SEMAFOROS;
	
	@Override
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Olá! Eu sou um agente Semáforo, meu id: "+ getAID().getName());
		
		createSensor();
		
		//Comportamento de perceber a quantidade de carros no ambiente do agente(rua), 
		//e mandar a mensagem com a quantidade de carros em seu ambiente
		addBehaviour(new TickerBehaviour(this, 10000) {
			
			@Override
			protected void onTick() {
				//Carros com seus respectivos tempos para chegarem ao semaforo
				sensor.getCarTime();
				
//				int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR, BOUND_COUNT_CAR);
//				
//				for (int i = 0; i < quantidadeDeCarros; i++) {
//					sensor.getStreet().addCar(new Car());
//				}
				
//				for (AID aid : sellerAgents) {
//					//Numero de carros e quantidade de semaforos
//					UtilMessage.sendInformMessage(street.getCars().size() + ":" + COUNT_SEMAFOROS, aid, getCurrentAgent());
//				}
//				sensor.getStreet().getCars().clear();
			}
		});
		
	}
	
	/**
	 * Metodo para finalizar o agente
	 */
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		System.out.println("Agente terminado!");
	}
	
	private void createSensor() {
		sensor = new Sensor();
		sensor.setDistanciaSensoriamento(100.0);
		sensor.setStreet(new Street(2, EnumTrackDirection.SENTIDO_UNICO));
	}

	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, AGENT_NAME + "-" + COUNT_SEMAFOROS++);
	}
}
