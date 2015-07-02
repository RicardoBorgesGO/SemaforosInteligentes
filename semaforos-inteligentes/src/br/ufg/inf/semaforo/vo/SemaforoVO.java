package br.ufg.inf.semaforo.vo;

import jade.core.AID;

@Deprecated
public class SemaforoVO {

	private AID aid;

	private Integer quantidadeDeCarros;

	/**
	 * @param aid
	 * @param quantidadeDeCarros
	 */
	public SemaforoVO(AID aid, Integer quantidadeDeCarros) {
		this.aid = aid;
		this.quantidadeDeCarros = quantidadeDeCarros;
	}

	public Integer getQuantidadeDeCarros() {
		return quantidadeDeCarros;
	}

	public void setQuantidadeDeCarros(Integer quantidadeDeCarros) {
		this.quantidadeDeCarros = quantidadeDeCarros;
	}

}
