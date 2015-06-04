package br.ufg.inf.semaforo.environment;

public class Car {

	private Double tamanho;
	private Double velocidadeMedia;

	/**
	 * 
	 */
	public Car() {
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

}
