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
	
	public static AID[] searchInYellowPages(String agentType, Agent myAgent) {
		AID[] sellerAgents = null;
		
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
		
		return sellerAgents;
	}
	
	public static AID[] searchInYellowPagesWithName(String agentName, Agent myAgent) {
		AID[] sellerAgents = null;
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(agentName);
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
		
		return sellerAgents;
	}
}
