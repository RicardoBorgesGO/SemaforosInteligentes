package br.ufg.inf.semaforo.environment;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import br.ufg.inf.semaforo.util.UtilRandom;

public class Sensor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8188966613703971453L;

	/**
	 * Quantidade minima de carros
	 */
	private static final Integer INIT_COUNT_CAR = 1;

	/**
	 * Quantidade maxima de carros por periodo
	 */
	private static final Integer BOUND_COUNT_CAR = 3;

	private Street street;

	/**
	 * Distancia do sensor em metros
	 */
	private Double distanciaSensoriamento;

	/**
	 * @param distanciaSensoriamento
	 */
	public Sensor(Double distanciaSensoriamento) {
		this.distanciaSensoriamento = distanciaSensoriamento;
	}
	
	public void start() {
		cognizeCar();

//		Map<Car, Double> carTime = new LinkedHashMap<Car, Double>();
//		Double time = 0.0;

//		for (Car car : street.getCars()) {
//			time = UtilVelocidadeMedia.calculaTempo(car.getVelocidadeMedia(), distanciaSensoriamento);
//			carTime.put(car, time);
//		}
		
		/**
		 * Temporizador em segundos
		 */
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				for (Car car : getStreet().getCars()) {
					car.setDistanciaDoSemaforo(distanciaSensoriamento - car.getVelocidadeMedia());
				}
			}
			
		}, 0, 1000);

	}

	public void cognizeCar() {
		int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR, BOUND_COUNT_CAR);

		for (int i = 0; i < quantidadeDeCarros; i++) {
			getStreet().addCar(new Car(10.0));
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
