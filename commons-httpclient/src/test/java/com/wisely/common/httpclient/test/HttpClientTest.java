package com.wisely.common.httpclient.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.wisely.common.httpclient.HttpClientComponent;
import com.wisely.common.httpclient.HttpClientManager;
import com.wisely.common.httpclient.model.HttpResult;

public class HttpClientTest {

	@Test
	public void testProperties() {
		CloseableHttpClient httpClient = HttpClientManager.createCloseableHttpClient(0) ;
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
		
		HttpResult result = HttpClientComponent.getInstance().doGet(url, params, null,0) ;
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
		
		HttpResult result = HttpClientComponent.getInstance().doPost(url,params, null,0) ;
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
		
		HttpResult result = HttpClientComponent.getInstance().doPostJson(url,JSONObject.toJSONString(params), null,0) ;
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
		
		HttpResult result = HttpClientComponent.getInstance().doDelete(url, params, null,0) ;
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
		
		HttpResult result = HttpClientComponent.getInstance().doPut(url, params, null,0) ;
		System.out.println(result);
	}
	
	// 测试https连接
	@Test
	public void testDoGetHttps() throws ClientProtocolException, IOException {
		String url = "https://127.0.0.1:8443/DemoServer/demo" ;
		HttpResult httpResult = HttpClientComponent.getInstance().doGet(url, null, 0) ;
		System.out.println(httpResult);
	}
	
	// 文件下载测试，读取文件流
	@Test
	public void testDownloadFile() throws IOException {
		String url = "http://docs.ebdoor.com/Image/CompanyCertificate/17/170609.jpg" ;
		InputStream in = HttpClientComponent.getInstance().doDownload(url, null, 0) ;
		OutputStream out = new FileOutputStream(new File("C:\\Users\\zhangtian\\Desktop\\zz.jpg")) ;
		IOUtils.copy(in, out, 1024) ;
	}
}
