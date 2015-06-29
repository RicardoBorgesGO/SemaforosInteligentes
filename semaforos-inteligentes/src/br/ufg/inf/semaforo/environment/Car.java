package br.ufg.inf.semaforo.environment;

import br.ufg.inf.semaforo.constant.EnumEstadoMovimentoCarro;

public class Car {

	private Double tamanho;
	private Double velocidadeMedia;
	private Double distanciaDoSemaforo;
	private EnumEstadoMovimentoCarro estadoMovimentoCarro;

	/**
	 * 
	 */
	public Car() {
	}

	/**
	 * @param velocidadeMedia
	 */
	public Car(Double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	/**
	 * @param distanciaDoSemaforo
	 * @param velocidadeMedia
	 */
	public Car(Double distanciaDoSemaforo, Double velocidadeMedia, EnumEstadoMovimentoCarro estadoMovimentoCarro) {
		this.distanciaDoSemaforo = distanciaDoSemaforo;
		this.velocidadeMedia = velocidadeMedia;
		this.estadoMovimentoCarro = estadoMovimentoCarro;
	}

	public Double getTamanho() {
		return tamanho;
	}

	public void setTamanho(Double tamanho) {
		this.tamanho = tamanho;
	}

	public Double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(Double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public Double getDistanciaDoSemaforo() {
		return distanciaDoSemaforo;
	}

	public void setDistanciaDoSemaforo(Double distanciaDoSemaforo) {
		this.distanciaDoSemaforo = distanciaDoSemaforo;
	}

	public EnumEstadoMovimentoCarro getEstadoMovimentoCarro() {
		return estadoMovimentoCarro;
	}

	public void setEstadoMovimentoCarro(EnumEstadoMovimentoCarro estadoMovimentoCarro) {
		this.estadoMovimentoCarro = estadoMovimentoCarro;
	}

}
