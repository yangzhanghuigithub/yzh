package com.learn.yzh.common.model;

import java.io.Serializable;

/**
 * 购买成功、退票成功
 */
public class OrderActionMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	public OrderActionMessage() {
	}

	public OrderActionMessage(String orderCode, int action, String cinemaCode) {
		this.orderCode = orderCode;
		this.action = action;
		this.cinemaCode = cinemaCode;
	}

	private String orderCode;

	private int action;

	private String cinemaCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getCinemaCode() {
		return cinemaCode;
	}

	public void setCinemaCode(String cinemaCode) {
		this.cinemaCode = cinemaCode;
	}
}
