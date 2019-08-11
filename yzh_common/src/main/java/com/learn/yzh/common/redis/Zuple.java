package com.learn.yzh.common.redis;


public class Zuple<T> {
	
	private Double score;
	private T element;
	
	public Zuple(T element, Double score) {
		this.element = element;
		this.score = score;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}
	
	
	
	
}
