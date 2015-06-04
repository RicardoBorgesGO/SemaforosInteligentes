package br.ufg.inf.semaforo.util;

import java.util.Random;

import jade.util.leap.Serializable;

public class UtilRandom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7918484558153512010L;
	
	/**
	 * Random de um numero iniciando pelo valor init passado como parametro ate o parametro bound,
	 * inclusive
	 * @param init
	 * @param bound
	 * @return
	 */
	public static Integer generateRandom(Integer init, Integer bound) {
		return new Random().nextInt(bound+1) + init;
	}

}
