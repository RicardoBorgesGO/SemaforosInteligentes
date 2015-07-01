package br.ufg.inf.semaforo.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.ufg.inf.semaforo.agent.TrafficLightAgent;
import br.ufg.inf.semaforo.constant.EnumEstadoMovimentoCarro;
import br.ufg.inf.semaforo.constant.EnumEstadoSemaforo;
import br.ufg.inf.semaforo.constant.EnumTrackDirection;
import br.ufg.inf.semaforo.util.UtilRandom;

public class Street {

	private Integer quantidadeDeVias;
	private EnumTrackDirection direction;
	private Double tamanhoDaVia;
	private Double tamanhoReservadoPorCarro;
	private List<Car> cars;
	private List<Car> carsExit;

	/**
	 * Quantidade minima de carros
	 */
	private static final Integer INIT_COUNT_CAR = 1;

	/**
	 * Quantidade maxima de carros por periodo
	 */
	private static final Integer BOUND_COUNT_CAR = 3;

	/**
	 * 
	 */
	public Street() {
	}

	/**
	 * @param quantidadeDeVias
	 * @param direction
	 */
	public Street(Integer quantidadeDeVias, EnumTrackDirection direction) {
		this.quantidadeDeVias = quantidadeDeVias;
		this.direction = direction;
	}

	/**
	 * @param quantidadeDeVias
	 * @param direction
	 * @param tamanhoDaVia
	 */
	public Street(Integer quantidadeDeVias, EnumTrackDirection direction,
			Double tamanhoDaVia, Double tamanhoBlocoDoCarro) {
		this.quantidadeDeVias = quantidadeDeVias;
		this.direction = direction;
		this.tamanhoDaVia = tamanhoDaVia;
		this.tamanhoReservadoPorCarro = tamanhoBlocoDoCarro;
	}

	public Integer getQuantidadeDeVias() {
		return quantidadeDeVias;
	}

	public void setQuantidadeDeVias(Integer quantidadeDeVias) {
		this.quantidadeDeVias = quantidadeDeVias;
	}

	public EnumTrackDirection getDirection() {
		return direction;
	}

	public void setDirection(EnumTrackDirection direction) {
		this.direction = direction;
	}

	public List<Car> getCars() {
		if (cars == null)
			cars = new ArrayList<Car>();
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	/**
	 * Tamanho da via em metros
	 * 
	 * @return
	 */
	public Double getTamanhoDaVia() {
		return tamanhoDaVia;
	}

	public void setTamanhoDaVia(Double tamanhoDaVia) {
		this.tamanhoDaVia = tamanhoDaVia;
	}

	public Double getTamanhoReservadoPorCarro() {
		return tamanhoReservadoPorCarro;
	}

	public void setTamanhoReservadoPorCarro(Double tamanhoBlocoDoCarro) {
		this.tamanhoReservadoPorCarro = tamanhoBlocoDoCarro;
	}

	public List<Car> getCarsExit() {
		if (carsExit == null)
			carsExit = new ArrayList<>();
		return carsExit;
	}

	public void setCarsExit(List<Car> carsExit) {
		this.carsExit = carsExit;
	}

	public void start() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				if (TrafficLightAgent.ESTADO_SEMAFORO
						.equals(EnumEstadoSemaforo.VERDE)) {
					try {
						for (Car car : cars) {
							if (car.getDistanciaDoSemaforo() < tamanhoReservadoPorCarro) {
								addCarExit(car);
								removeCar(car);
							}

							runCar(car);
						}
					} catch (Exception e) {

					}
				}
			}

		}, 0, 1000);
	}

	public void cognizeCar() {
		int quantidadeDeCarros = UtilRandom.generateRandom(INIT_COUNT_CAR,
				BOUND_COUNT_CAR);

		System.out.println("Chegaram mais " + quantidadeDeCarros + " carros\n");

		for (int i = 0; i < quantidadeDeCarros; i++) {
			addCar(new Car(tamanhoDaVia, 10.0,
					EnumEstadoMovimentoCarro.EM_MOVIMENTO));
		}
	}

	/**
	 * Fazer o carro andar
	 * 
	 * @param car
	 */
	public void runCar(Car car) {
		car.setDistanciaDoSemaforo(car.getDistanciaDoSemaforo()
				- car.getVelocidadeMedia());
	}

	/**
	 * Adiciona carro na lista de carros de uma rua
	 * 
	 * @param car
	 */
	public void addCar(Car car) {
		getCars().add(car);
	}

	/**
	 * Remove carro na lista de carros de uma rua
	 * 
	 * @param car
	 */
	public void removeCar(Car car) {
		getCars().remove(car);
	}
	
	/**
	 * Adiciona carro na lista de carros de saida de uma rua
	 * 
	 * @param car
	 */
	public void addCarExit(Car car) {
		getCarsExit().add(car);
	}
	
	/**
	 * Remove carro na lista de carros de saida de uma rua
	 * 
	 * @param car
	 */
	public void removeCarExit(Car car) {
		getCarsExit().remove(car);
	}
}
