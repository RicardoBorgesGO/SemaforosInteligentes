package br.ufg.inf.semaforo.environment;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.semaforo.constant.EnumTrackDirection;

public class Street {

	private Integer quantidadeDeVias;
	private EnumTrackDirection direction;
	private List<Car> cars;

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
	 * Adiciona carros na lista de carros de uma rua
	 * @param car
	 */
	public void addCar(Car car) {
		getCars().add(car);
	}

}
