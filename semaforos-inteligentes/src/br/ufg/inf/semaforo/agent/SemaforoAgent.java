package br.ufg.inf.semaforo.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import br.ufg.inf.semaforo.constant.EnumEstadoSemaforo;
import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.environment.Car;
import br.ufg.inf.semaforo.environment.Street;
import br.ufg.inf.semaforo.util.UtilAgent;
import br.ufg.inf.semaforo.util.UtilMessage;
import br.ufg.inf.semaforo.util.UtilRandom;

public class SemaforoAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2119467267589466071L;

	public static Integer COUNT_SEMAFOROS = 0;
	
	public static String TYPE_AGENT = "semaforo-agent";
	
	public static String AGENT_NAME = "semaforo";
	
	/**
	 * Quantidade minima de carros
	 */
	private static final Integer INIT_COUNT_CAR = 3;
	
	/**
	 * Quantidade maxima de carros por periodo
	 */
	private static final Integer BOUND_COUNT_CAR = 10;
	
	private AID[] sellerAgents;
	
	private AID sellerControlador;
	
	//Escopo com um semaforo por rua
	private Street street;
	
	private EnumEstadoSemaforo estadoSemaforo;
	
	/**
	 * Metodo chamado ao criar o agente
	 */
	@Override
	@SuppressWarnings("serial")
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Olá! Eu sou um agente Semáforo, meu id é: "+ getAID().getName());
		
		createStreet();
		registerInYellowPages();
		
		/**
		 * Tipos de behavior
		 * Waker - relacionado a acordar.
		 * Ticker - acontece de tempo em tempo, intervalo de tempo.
		 * Cyclic - relacionado 
		 */
		
		//TODO Substituir pela classe do comportamento criada
		//Comportamento de procurar todos os agentes
		addBehaviour(new TickerBehaviour(this, 5000) {
			
			@Override
			protected void onTick() {
				// Atualiza a lista de agentes do tipo TYPE_AGENT
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
//				sd.setType(ControladorAgent.TYPE_AGENT);
				sd.setName(ControladorAgent.AGENT_NAME);
				template.addServices(sd);
				
				try {
					//TODO Restringir em apenas 1 agente controlador
					DFAgentDescription[] result = DFService.search(myAgent, template);
					sellerAgents = new AID[result.length];
					for (int i = 0; i < result.length; ++i) {
						sellerAgents[i] = result[i].getName();
					}
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		});
		
		//Comportamento de perceber a quantidade de carros no ambiente do agente(rua), 
		//e mandar a mensagem com a quantidade de carros em seu ambiente
		addBehaviour(new TickerBehaviour(this, 10000) {
			
			@Override
			protected void onTick() {
				int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR, BOUND_COUNT_CAR);
				
				for (int i = 0; i < quantidadeDeCarros; i++) {
					street.addCar(new Car());
				}
				
				for (AID aid : sellerAgents) {
					UtilMessage.sendInformMessage("Carros:" + street.getCars().size(), aid, getCurrentAgent());
				}
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
	
	/**
	 * Inicializa as ruas do ambiente 
	 */
	private void createStreet() {
		street = new Street(2, EnumTrackDirection.SENTIDO_DUPLO);
	}

	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, AGENT_NAME + "-" + COUNT_SEMAFOROS++);
	}
	
	public Agent getCurrentAgent() {
		return this;
	}
}
