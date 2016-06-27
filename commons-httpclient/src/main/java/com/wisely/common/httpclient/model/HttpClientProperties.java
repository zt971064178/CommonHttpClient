package com.wisely.common.httpclient.model;

import com.wisely.common.httpclient.annotation.PropertyConfigiration;
import com.wisely.common.httpclient.annotation.Value;

@PropertyConfigiration(location="httpclient.properties")
public class HttpClientProperties {
	@Value("${httpClient.maxTotal}")
	private int maxTotal ;
	@Value("${httpClient.defaultMaxPerRoute}")
	private int defaultMaxPerRoute ;
	@Value("${httpClient.connectTimeout}")
	private int connectTimeout ;
	@Value("${httpClient.connectionRequestTimeout}")
	private int connectionRequestTimeout ;
	@Value("${httpClient.socketTimeout}")
	private int socketTimeout ;
	@Value("${httpClient.validateAfterInactivity}")
	private int validateAfterInactivity ;
	@Value("${httpClient.isRetryConnection}")
	private boolean isRetryConnection ;// 是否开启断线重连开关

	public int getValidateAfterInactivity() {
		return validateAfterInactivity;
	}

	public void setValidateAfterInactivity(int validateAfterInactivity) {
		this.validateAfterInactivity = validateAfterInactivity;
	}

	public boolean isRetryConnection() {
		return isRetryConnection;
	}

	public void setRetryConnection(boolean isRetryConnection) {
		this.isRetryConnection = isRetryConnection;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}
}
