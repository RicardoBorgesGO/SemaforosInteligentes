package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.ufg.inf.semaforo.constant.EnumEstadoMovimentoCarro;
import br.ufg.inf.semaforo.constant.EnumEstadoSemaforo;
import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.environment.Car;
import br.ufg.inf.semaforo.environment.Sensor;
import br.ufg.inf.semaforo.environment.Street;
import br.ufg.inf.semaforo.util.UtilAgent;
import br.ufg.inf.semaforo.util.UtilMath;

public class TrafficLightAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446136644934480233L;
	
	private Sensor sensor;
	
	public static EnumEstadoSemaforo ESTADO_SEMAFORO = EnumEstadoSemaforo.VERMELHO;
	
	public static String TYPE_AGENT = "trafficLightAgent";
	
	private static String AGENT_NAME = "trafficLight";
	
	private static Integer COUNT_SEMAFOROS = 0;
	
	/**
	 * Tempo que o semaforo ficara aberto em segundos
	 */
	private static Integer OPEN_TIME = 5;
	
//	private Integer quantidadeDeCarrosParaLiberar = 0;
	
	@Override
	@SuppressWarnings("serial")
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Olá! Eu sou um agente Semáforo, meu id: "+ getAID().getName());
		
		createSensor();
		registerInYellowPages();
		
		sensor.getStreet().start();
		
		//Comportamento de perceber a quantidade de carros no ambiente do agente(rua), 
		//e mandar a mensagem com a quantidade de carros em seu ambiente
		addBehaviour(new TickerBehaviour(this, 10000) {
			
			@Override
			protected void onTick() {
				sensor.start();
			}
		});
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			
			@Override
			protected void onTick() {
				List<Car> cars = sensor.getStreet().getCars();
				
				for (Car car : cars) {
					System.out.println("Carro" + car.getDistanciaDoSemaforo() +" - Estado do movimento do carro: " + car.getEstadoMovimentoCarro());
				}
				
				Integer countCarInVia = (int) ((sensor.getStreet().getTamanhoDaVia()/sensor.getStreet().getTamanhoReservadoPorCarro())*sensor.getStreet().getQuantidadeDeVias());
				
				//Metodo countCarInVia é passado a porcentagem de carros esperados
				if (UtilMath.calcAfterPercent(countStopCar(cars), countCarInVia, 30.0) && ESTADO_SEMAFORO.equals(EnumEstadoSemaforo.VERMELHO)) {
					liberarSemaforo();
				}
				
				System.out.println("Estado semaforo: " + ESTADO_SEMAFORO);
				System.out.println("Percentagem de carros parados: " + UtilMath.calcAfterPercent(countStopCar(cars), countCarInVia, 30.0));
			}
		});
	}
	
	private Double countStopCar(List<Car> cars) {
		Double count = 0.0;
		
		for (Car car : cars) {
			if (car.getEstadoMovimentoCarro().equals(EnumEstadoMovimentoCarro.PARADO))
				count++;
		}
		
		return count;
	}
	
//	private Set<Car> countToReleaseCars(Map<Car, Double> cars) {
//		Set<Car> carrosParaLiberar = new LinkedHashSet<Car>();
//		
//		for (Car car : cars.keySet()) {
//			Double time = cars.get(car);
//			
//			if (time < 2)
//				carrosParaLiberar.add(car);
//		}
//		
//		return carrosParaLiberar;
//	}
	
	private void liberarSemaforo() {
		ESTADO_SEMAFORO = EnumEstadoSemaforo.VERDE;
		
		Timer timer = new Timer();
		
		TimerTask timerTask = new TimerTask() {
			int segundosAberto = 0;
			
			@Override
			public void run() {
				segundosAberto++;
				
				if (segundosAberto == OPEN_TIME) {
					fecharSemaforo();
					timer.cancel();
				}
			}
		};
		
		timer.schedule(timerTask, 0, 1000);
		
	}
	
	private void fecharSemaforo() {
		ESTADO_SEMAFORO = EnumEstadoSemaforo.AMARELO;
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				ESTADO_SEMAFORO = EnumEstadoSemaforo.VERMELHO;
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
		sensor.setStreet(new Street(2, EnumTrackDirection.SENTIDO_UNICO, 100.0, 10.0));
	}

	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, AGENT_NAME + "-" + COUNT_SEMAFOROS++);
	}
}
