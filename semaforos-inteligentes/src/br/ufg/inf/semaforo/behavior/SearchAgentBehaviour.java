package br.ufg.inf.semaforo.behavior;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SearchAgentBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049747893614887970L;

	private String agentType;

	private AID[] sellerAgents;

	public SearchAgentBehaviour(Agent a, long period, String agentType) {
		super(a, period);
		this.agentType = agentType;
	}

	@Override
	protected void onTick() {
		// Atualiza a lista de agentes do tipo TYPE_AGENT
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(agentType);
		template.addServices(sd);

		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			sellerAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				sellerAgents[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	public AID[] getSellerAgents() {
		return sellerAgents;
	}

	public void setSellerAgents(AID[] sellerAgents) {
		this.sellerAgents = sellerAgents;
	}

}
