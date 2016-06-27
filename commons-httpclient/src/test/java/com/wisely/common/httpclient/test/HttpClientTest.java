package com.wisely.common.httpclient.test;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import com.wisely.common.httpclient.HttpClientManager;
import com.wisely.common.httpclient.IdleConnectionEvictor;

public class HttpClientTest {

	@Test
	public void testProperties() {
		CloseableHttpClient httpClient = HttpClientManager.createCloseableHttpClient() ;
		System.out.println(httpClient);
	}
	
	public static void main(String[] args) {
		new IdleConnectionEvictor(HttpClientManager.httpClientConnectionManager).run();
	}
}
