package br.ufg.inf.semaforo.util;

public class UtilVelocidadeMedia {

	/**
	 * Calcula o tempo com a distancia e a velocidade media
	 * @param velocidadeMedia
	 * @param distancia
	 * @return
	 */
	public static Double calculaTempo(Double velocidadeMedia, Double distancia) {
		return distancia/velocidadeMedia;
	}
	
}
