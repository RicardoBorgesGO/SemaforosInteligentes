package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import br.ufg.inf.semaforo.constant.EnumEstadoSemaforo;
import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.environment.Car;
import br.ufg.inf.semaforo.environment.Sensor;
import br.ufg.inf.semaforo.environment.Street;
import br.ufg.inf.semaforo.util.UtilAgent;

public class TrafficLightAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446136644934480233L;
	
	private Sensor sensor;
	
	private EnumEstadoSemaforo estadoSemaforo = EnumEstadoSemaforo.VERMELHO;
	
	public static String TYPE_AGENT = "trafficLightAgent";
	
	private static String AGENT_NAME = "trafficLight";
	
	private static Integer COUNT_SEMAFOROS = 0;
	
//	private Integer quantidadeDeCarrosParaLiberar = 0;
	
	@Override
	@SuppressWarnings("serial")
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Olá! Eu sou um agente Semáforo, meu id: "+ getAID().getName());
		
		createSensor();
		registerInYellowPages();
		
		//Comportamento de perceber a quantidade de carros no ambiente do agente(rua), 
		//e mandar a mensagem com a quantidade de carros em seu ambiente
		addBehaviour(new TickerBehaviour(this, 10000) {
			
			@Override
			protected void onTick() {
				sensor.start();
//				System.out.println("Tamanho do Map: " + carTime.size());
				
				/**
				 * Temporizador em segundos
				 */
//				new Timer().schedule(new TimerTask() {
//					
//					@Override
//					public void run() {
//						
//						for (Car car : carTime.keySet()) {
//							Double time = carTime.get(car);
//							time--;
//							
//							if (time >= 0)
//								carTime.put(car, time);
//							
//						}
//					}
//					
//				}, 0, 1000);
			}
		});
		
	}
	
	private Set<Car> countToReleaseCars(Map<Car, Double> cars) {
		Set<Car> carrosParaLiberar = new LinkedHashSet<Car>();
		
		for (Car car : cars.keySet()) {
			Double time = cars.get(car);
			
			if (time < 2)
				carrosParaLiberar.add(car);
		}
		
		return carrosParaLiberar;
	}
	
	private void liberarSemaforo() {
		estadoSemaforo = EnumEstadoSemaforo.VERDE;
	}
	
	private void fecharSemaforo() {
		estadoSemaforo = EnumEstadoSemaforo.AMARELO;
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				estadoSemaforo = EnumEstadoSemaforo.VERMELHO;
			}
			
		}, 3000);
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
		sensor = new Sensor(100.0);
		sensor.setStreet(new Street(2, EnumTrackDirection.SENTIDO_UNICO));
	}

	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, AGENT_NAME + "-" + COUNT_SEMAFOROS++);
	}
}
