package br.ufg.inf.semaforo.environment;

public class Car {

	private Double tamanho;
	private Double velocidadeMedia;
	private Double distanciaDoSemaforo;

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
	 * @param tamanho
	 * @param velocidadeMedia
	 */
	public Car(Double tamanho, Double velocidadeMedia) {
		this.tamanho = tamanho;
		this.velocidadeMedia = velocidadeMedia;
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

}
