package br.ufg.inf.semaforo.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.semaforo.util.UtilAgent;
import br.ufg.inf.semaforo.vo.SemaforoVO;

public class ControladorAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4004995602039258629L;
	
	public static String TYPE_AGENT = "controlador-agent";
	
	public static String AGENT_NAME = "controlador1";
	
	private static Integer COUNT_SENDERS = 0;
	
//	private List<AID> sendersAgent;
	
	private List<SemaforoVO> semaforos;
	
	@Override
	protected void setup() {
		System.out.println("Agente criado!");
		System.out.println("Ol�! Eu sou um agente Controlador, meu id �: "+ getAID().getName());
		
		registerInYellowPages();
		
		//Comportamento de receber mensagem dos agentes
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				MessageTemplate mtInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage messageInform = myAgent.receive(mtInform);
				
				MessageTemplate mTemplateCFP = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				ACLMessage messageCFP = myAgent.receive(mTemplateCFP);

				if (messageInform != null) {
					String content = messageInform.getContent();
					Integer delimiter = content.indexOf(":");
					
					String quantidadeDeCarrosStr = content.substring(0, delimiter);
					String quantidadeDeSemaforosStr = content.substring(delimiter+1, content.length());
					
					Integer quantidadeDeCarros = Integer.parseInt(quantidadeDeCarrosStr);
					Integer quantidadeDeSemaforos = Integer.parseInt(quantidadeDeSemaforosStr);
					
					//TODO Verificar se recebeu de todos os carros e nao a quantidade de mensagem ser a mesma dos carros
					addSendersAgent(new SemaforoVO(messageInform.getSender(), quantidadeDeCarros));
					
					COUNT_SENDERS++;
					
					System.out.println("Quantidade de carros: " + quantidadeDeCarros);
					System.out.println("Quantidade de carros: " + quantidadeDeSemaforos);
					
					if (quantidadeDeSemaforos.equals(COUNT_SENDERS)) {
						//TODO executar metodo quando todos enviarem a mensagem
						System.out.println("Opa! Recebeu todos.");
						
						COUNT_SENDERS = 0;
					}
				} else if (messageCFP != null) {
					//TODO verificar a necessidade desta condicao
				} else {
					// TODO Testar sem o block();
					block();
				}
			}
		});
		
		addBehaviour(new TickerBehaviour(this, 5000) {
			
			@Override
			protected void onTick() {
//				for (AID aid : sendersAgent) {
//					aid.get
//				}
			}
		});
	}
	
	/**
	 * Registra os agentas nas paginas amarelas (DFAgent)
	 */
	private void registerInYellowPages() {
		UtilAgent.registerInYellowPages(getAID(), this, TYPE_AGENT, AGENT_NAME);
	}

	public List<SemaforoVO> getSendersAgent() {
		if (semaforos == null)
			semaforos = new ArrayList<SemaforoVO>();
		return semaforos;
	}
	
	private void addSendersAgent(SemaforoVO semaforoVO) {
		getSendersAgent().add(semaforoVO);
	}
	
}
