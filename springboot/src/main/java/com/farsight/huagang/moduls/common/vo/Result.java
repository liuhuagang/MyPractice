package com.farsight.huagang.moduls.common.vo;


public class Result {

	private int status;
	private String message;
	private Object object;

	public Result() {
	}

	public Result(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Result(int status, String message, Object object) {
		this.status = status;
		this.message = message;
		this.object = object;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}


	public enum ResultStatus {
		SUCCESS(200), FAILED(500);
		
		public int status;

		private ResultStatus(int status) {
			this.status = status;
		}
	}
	
}
