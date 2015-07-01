package br.ufg.inf.semaforo.environment;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import br.ufg.inf.semaforo.constant.EnumEstadoMovimentoCarro;

public class Sensor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8188966613703971453L;

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
		street.cognizeCar();

		/**
		 * Temporizador em segundos
		 */
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				for (Car car : getStreet().getCars()) {
					if (car.getDistanciaDoSemaforo() >= 10 && verificaQuantidadeDeCarrosPorVia(car))
						street.runCar(car);
					
					System.out.println("Distancia do semaforo: " + car.getDistanciaDoSemaforo());
				}
				
				verificaCarrosParados();
				
				System.out.println("=========================\n");
				System.out.println("Quantidade de carros: " + getStreet().getCars().size());
			}
			
		}, 0, 1000);

	}
	
	private boolean verificaQuantidadeDeCarrosPorVia(Car car) {
		int quantidadeDeCarrosNaMesmaDistancia = 0;
		
		for (Car carThis : getStreet().getCars()) {
			if (carThis.getDistanciaDoSemaforo().equals(car.getDistanciaDoSemaforo() - car.getVelocidadeMedia())) {
				quantidadeDeCarrosNaMesmaDistancia++;
			}
		}
		
		if (quantidadeDeCarrosNaMesmaDistancia == getStreet().getQuantidadeDeVias())
			return false;
		
		return true;
	}
	
	private boolean verificaCarrosParados() {
		int quantidadeDeCarrosNaMesmaDistancia = 0;
		Double distanciaDeParada = 0.0;
		
		for (Car car : getStreet().getCars()) {
			if (car.getDistanciaDoSemaforo().equals(distanciaDeParada)) {
				car.setEstadoMovimentoCarro(EnumEstadoMovimentoCarro.PARADO);
				quantidadeDeCarrosNaMesmaDistancia++;
			}
			
			if (quantidadeDeCarrosNaMesmaDistancia == getStreet().getQuantidadeDeVias()) {
				quantidadeDeCarrosNaMesmaDistancia = 0;
				distanciaDeParada += car.getVelocidadeMedia();
			}
		}
		return false;
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
