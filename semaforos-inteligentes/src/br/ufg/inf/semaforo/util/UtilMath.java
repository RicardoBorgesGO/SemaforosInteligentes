package br.ufg.inf.semaforo.util;

public class UtilMath {

	/**
	 * Calcula o tempo com a distancia e a velocidade media
	 * @param velocidadeMedia
	 * @param distancia
	 * @return
	 */
	public static Double calculaTempo(Double velocidadeMedia, Double distancia) {
		return distancia/velocidadeMedia;
	}
	
	/**
	 * Calcula a percentagem e verifica se está acima do percentual desejado
	 * @param countObject
	 * @param maxCount
	 * @param percent
	 * @return
	 */
	public static boolean calcAfterPercent(Double countObject, Integer maxCount, Double percent) {
		Double percentCalc = 0.0;
		
		if (maxCount > 0)
			percentCalc = (double) ((countObject/maxCount)*100);
		
		return percentCalc >= percent;
	}
	
}
