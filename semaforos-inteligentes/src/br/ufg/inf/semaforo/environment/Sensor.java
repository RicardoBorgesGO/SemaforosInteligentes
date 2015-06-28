package br.ufg.inf.semaforo.environment;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufg.inf.semaforo.util.UtilRandom;
import br.ufg.inf.semaforo.util.UtilVelocidadeMedia;

public class Sensor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8188966613703971453L;
	
	/**
	 * Quantidade minima de carros
	 */
	private static final Integer INIT_COUNT_CAR = 3;
	
	/**
	 * Quantidade maxima de carros por periodo
	 */
	private static final Integer BOUND_COUNT_CAR = 10;

	private Street street;

	private Double distanciaSensoriamento;
	
	public Map<Car, Double> getCarTime() {
		cognizeCar();
		
		Map<Car, Double> carTime = new LinkedHashMap<Car, Double>();
		Double time = 0.0;
		
		for (Car car : street.getCars()) {
			time = UtilVelocidadeMedia.calculaTempo(car.getVelocidadeMedia(), distanciaSensoriamento);
			carTime.put(car, time);
		}
		
		return carTime;
	}
	
	private void cognizeCar() {
		int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR, BOUND_COUNT_CAR);
		
		for (int i = 0; i < quantidadeDeCarros; i++) {
			getStreet().addCar(new Car());
		}
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
