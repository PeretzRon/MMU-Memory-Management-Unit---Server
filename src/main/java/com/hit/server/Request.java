package com.hit.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Request<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, String> headers;
	private T body;

	public Request(Map<String, String> headers, T body) {
		super();
		this.headers = new HashMap<>();
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Requests:\nAction: " + headers.get("action") + "\n" + Arrays.deepToString((Object[]) (this.getBody()));

	}

}
