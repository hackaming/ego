package com.ego.commons.pojo;

public class EgoResult {
	private int status;
	private Object msg;
	private Object data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getMessage() {
		return msg;
	}

	public void setMessage(Object message) {
		this.msg = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
