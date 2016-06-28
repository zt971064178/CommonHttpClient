package com.wisely.common.httpclient.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.wisely.common.httpclient.HttpClientComponent;
import com.wisely.common.httpclient.HttpClientManager;
import com.wisely.common.httpclient.model.HttpResult;

public class HttpClientTest {

	@Test
	public void testProperties() {
		CloseableHttpClient httpClient = HttpClientManager.createCloseableHttpClient() ;
		System.out.println(httpClient);
	}
	
	public static void main(String[] args) {
		HttpClientComponent.getInstance() ;
	}
	
	@Test
	public void testDoGet() throws Exception {
		/*
		 * @Path("/httpClient")
		   @Get
		   @Consumes({MediaType.JSON})
		 */
		String url = "http://127.0.0.1:8080/wx/approval/httpClient" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("username", "金鸡湖") ;
		params.put("age", "22") ;
		
		HttpResult result = HttpClientComponent.getInstance().doGet(url, params, null) ;
		System.out.println(result);
	}
	
	@Test
	public void testDoPost() throws Exception {
		/*
		 * @Path("/httpClient")
		   @POST
		   @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
		 */
		String url = "http://127.0.0.1:8080/wx/approval/httpClient" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("username", "金鸡湖") ;
		params.put("age", "22") ;
		
		HttpResult result = HttpClientComponent.getInstance().doPost(url,params, null) ;
		System.out.println(result);
	}
	
	@Test
	public void testDoJson() throws Exception {
		/*
		 * @Path("/httpClient")
		   @POST
		   @Produces({MediaType.APPLICATION_JSON})
		 */
		String url = "http://127.0.0.1:8080/wx/approval/httpClient" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("username", "金鸡湖") ;
		params.put("age", "22") ;
		
		HttpResult result = HttpClientComponent.getInstance().doPostJson(url,JSONObject.toJSONString(params), null) ;
		System.out.println(result);
	}
	
	@Test
	public void testDoDelete() throws Exception {
		/*
		 * @Path("/httpClient")
		   @DELETE
		   @Produces({MediaType.APPLICATION_JSON})
		 */
		String url = "http://127.0.0.1:8080/wx/approval/httpClient" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("username", "金鸡湖") ;
		params.put("age", "22") ;
		
		HttpResult result = HttpClientComponent.getInstance().doDelete(url, params, null) ;
		System.out.println(result);
	}
	
	@Test
	public void testDoPut() throws Exception {
		/*
		 * @Path("/httpClient")
		   @PUT
		   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		   public String testHttpClient(@FormParam("username") String param, @FormParam("age") int age)
		 */
		String url = "http://127.0.0.1:8080/wx/approval/httpClient" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("username", "金鸡湖") ;
		params.put("age", "22") ;
		
		HttpResult result = HttpClientComponent.getInstance().doPut(url, params, null) ;
		System.out.println(result);
	}
}
