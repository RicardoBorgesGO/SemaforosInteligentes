package br.ufg.inf.semaforo.util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class UtilAgent {

	public static void registerInYellowPages(AID aid, Agent agent, String agentType, String agentName) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(aid);
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType(agentType);
		sd.setName(agentName);
		
		dfd.addServices(sd);
		try {
			DFService.register(agent, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}
