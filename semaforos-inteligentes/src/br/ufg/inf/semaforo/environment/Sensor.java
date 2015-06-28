package br.ufg.inf.semaforo.environment;

import java.io.Serializable;

import br.ufg.inf.semaforo.constant.EnumEstadoSemaforo;
import br.ufg.inf.semaforo.util.UtilVelocidadeMedia;

public class Sensor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8188966613703971453L;

	private Street street;

	private Double distanciaSensoriamento;
	
	public EnumEstadoSemaforo getStatus() {
		Double time = 0.0;
		
		for (Car car : street.getCars()) {
			time = UtilVelocidadeMedia.calculaTempo(car.getVelocidadeMedia(), distanciaSensoriamento);
		}
		
		return null;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Double getDistanciaSensoriamento() {
		return distanciaSensoriamento;
	}

	public void setDistanciaSensoriamento(Double distanciaSensoriamento) {
		this.distanciaSensoriamento = distanciaSensoriamento;
	}

}
