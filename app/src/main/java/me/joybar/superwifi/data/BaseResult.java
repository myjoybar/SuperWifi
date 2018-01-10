package me.joybar.superwifi.data;

/**
 * Created by joybar on 2018/1/9.
 */

public class BaseResult<T> {

	private Integer code;
	private String message;
	private Long responseTime;
	private T data;

	public BaseResult() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
