package com.dreamer8.yosimce.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

public class CustomRpcRequestBuilder extends RpcRequestBuilder{
	private int timeout = 8000;

	public CustomRpcRequestBuilder() {
	}

	public CustomRpcRequestBuilder(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		RequestBuilder builder = super.doCreate(serviceEntryPoint);
		if(timeout>0){
			builder.setTimeoutMillis(this.timeout);
		}
		return builder;
	}
}
