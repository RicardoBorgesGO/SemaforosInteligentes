package br.ufg.inf.semaforo.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

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
import br.ufg.inf.semaforo.util.UtilMessage;

public class TrafficLightAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8446136644934480233L;

	private Sensor sensor;

	public static EnumEstadoSemaforo ESTADO_SEMAFORO = EnumEstadoSemaforo.VERMELHO;

	public static String TYPE_AGENT = "trafficLightAgent";

	private String agentName = "trafficLight";

	private static Integer COUNT_SEMAFOROS = 0;

	private Integer numberTrafficLight = 0;

	/**
	 * Tempo que o semaforo ficara aberto em segundos
	 */
	private static Integer OPEN_TIME = 5;

	private AID[] sellerAgents;

	@Override
	@SuppressWarnings("serial")
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Olá! Eu sou um agente Semáforo, meu id: "
				+ getAID().getName());

		createAgentName();

		createSensor();
		registerInYellowPages();

		sensor.getStreet().start();

		// Comportamento de perceber a quantidade de carros no ambiente do
		// agente(rua),
		// e mandar a mensagem com a quantidade de carros em seu ambiente
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

				System.out.println("=========================");
				System.out.println("AGENTE: " + agentName);

				for (Car car : cars) {
					System.out.println("Distância do Carro ao semáforo: "
							+ car.getDistanciaDoSemaforo()
							+ " - Estado do movimento do carro: "
							+ car.getEstadoMovimentoCarro());
				}

				Integer countCarInVia = (int) ((sensor.getStreet()
						.getTamanhoDaVia() / sensor.getStreet()
						.getTamanhoReservadoPorCarro()) * sensor.getStreet()
						.getQuantidadeDeVias());

				// Metodo countCarInVia é passado a porcentagem de carros
				// esperados
				if (UtilMath.calcAfterPercent(countStopCar(cars),
						countCarInVia, 30.0)
						&& ESTADO_SEMAFORO.equals(EnumEstadoSemaforo.VERMELHO)) {
					liberarSemaforo();
				}

				System.out.println("Estado semaforo: " + ESTADO_SEMAFORO);
//				System.out.println("Percentagem de carros parados: "
//						+ UtilMath.calcAfterPercent(countStopCar(cars),
//								countCarInVia, 10.0));
			}
		});

		// Comportamento de procurar todos os agentes no ambiente
		addBehaviour(new TickerBehaviour(this, 3000) {

			@Override
			protected void onTick() {
				sellerAgents = UtilAgent.searchInYellowPagesWithName(
						searchNameNextAgent(), myAgent);
			}
		});

		// Comportamento de enviar os carros que sairam do agente semaforo atual
		addBehaviour(new TickerBehaviour(this, 1000) {

			@Override
			protected void onTick() {
				if (sellerAgents != null && sellerAgents.length > 0) {
					AID aid = sellerAgents[0];
					Car car = sensor.getStreet().cognizeNextCar();

					if (car != null) {
						UtilMessage.sendObjectMessage(car, aid,
								getCurrentAgent(), new ACLMessage(
										ACLMessage.INFORM));
					}
				}
			}
		});

		// Comportamento de receber mensagem de outros agentes
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				MessageTemplate mtInform = MessageTemplate
						.MatchPerformative(ACLMessage.INFORM);
				ACLMessage messageInform = myAgent.receive(mtInform);

				if (messageInform != null) {
					try {
						Car car = (Car) messageInform.getContentObject();
						sensor.getStreet().addWarnNewCar(car);
					} catch (UnreadableException e) {
						e.printStackTrace();
					}
				} else {
					block();
				}
			}
		});
	}

	private Agent getCurrentAgent() {
		return this;
	}

	private void createAgentName() {
		numberTrafficLight = COUNT_SEMAFOROS;
		agentName = agentName + "-" + COUNT_SEMAFOROS++;
	}

	private String searchNameNextAgent() {
		Integer num = numberTrafficLight;
		String nextAgentName = agentName.replace(num.toString(), "");
		num++;

		return nextAgentName.concat(num.toString());
	}

	public static void main(String[] args) {
		TrafficLightAgent agent = new TrafficLightAgent();
		agent.searchNameNextAgent();
	}

	private Double countStopCar(List<Car> cars) {
		Double count = 0.0;

		for (Car car : cars) {
			// TODO Modificar medida para calcular a quantidade de carros que
			// passaram do sensor, ou seja, menor
			// que 100m
			// if
			// (car.getEstadoMovimentoCarro().equals(EnumEstadoMovimentoCarro.PARADO))
			// count++;
			if (car.getDistanciaDoSemaforo() < sensor.getStreet()
					.getTamanhoDaVia())
				count++;
		}

		return count;
	}

	private void liberarSemaforo() {
		ESTADO_SEMAFORO = EnumEstadoSemaforo.VERDE;

		final Timer timer = new Timer();

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
		sensor.setStreet(new Street(2, EnumTrackDirection.SENTIDO_UNICO, 100.0,
				10.0));
	}

	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, agentName);
	}
}
