package com.wisely.common.httpclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.wisely.common.httpclient.model.HttpResult;

/**
 * HttpClient调用客户端
 */
public class HttpClientComponent {
	
	private HttpClientComponent() {
		// 初试加载启动线程，清除无效链接
		new IdleConnectionEvictor(HttpClientManager.httpClientConnectionManager).executeClearIdleConnection() ;
	}
	
	// 静态内部类创建单例  线程安全
	private static class SingletonHolder {
		private final static HttpClientComponent INSTANCE = new HttpClientComponent();
    }
	
	// 获取实例
	public static final HttpClientComponent getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	// =========================== Get请求  ===========================
	/**
	 * 执行GET请求--无参数
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doGet(String url, Map<String, String> headers) throws ClientProtocolException, IOException {
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(HttpClientManager.getRequestConfig());
		// 构造头部信息
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpGet.setHeader(header.getKey(), header.getValue());
			}
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	/**
	 * 带有参数的GET请求
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String doGet(String url, Map<String, String> params, Map<String, String> headers)
			throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}
		return this.doGet(uriBuilder.build().toString(), headers);
	}

	// =========================== Post请求  ===========================
	/**
	 * 执行POST请求
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(HttpClientManager.getRequestConfig());
		// 构造头部信息
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpPost.setHeader(header.getKey(), header.getValue());
				//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				//httpPost.setHeader("Authorization", "Bearer "+token);
				//httpPost.setHeader("Accept", "application/json");
			}
		}
		
		if (params != null) {
			// 设置2个post参数，一个是scope、一个是q
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 执行POST请求--无参数
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> headers) throws IOException {
		return this.doPost(url, null, headers);
	}

	/**
	 * 提交json数据
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPostJson(String url, String json, Map<String, String> headers) throws ClientProtocolException, IOException {
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(HttpClientManager.getRequestConfig());
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpPost.setHeader(header.getKey(), header.getValue());
				//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				//httpPost.setHeader("Authorization", "Bearer "+token);
				//httpPost.setHeader("Accept", "application/json");
			}
		}
		
		if (json != null) {
			// 构造一个form表单式的实体
			/**
			 * 这里是json数据的请求头，所以服务端需要是下面的json请求头，否则无法接收到json串数据
			 *  @Path("demo")
				@POST
				@Produces("application/json")
				public String demo(String json)
			 */
			StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(stringEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	// =========================== Put请求  ===========================
	/**
	 *  doPut:(无参传递). 
	 *  @return_type:HttpResult
	 *  @author zhangtian 
	 *  @param url
	 *  @param headers
	 *  @return
	 *  @throws IOException
	 */
	public HttpResult doPut(String url, Map<String, String> headers) throws IOException {
		return this.doPost(url, null, headers);
	}
	
	/**
	 *  doPut:(携带参数调用). 
	 *  @return_type:HttpResult
	 *  @author zhangtian 
	 *  @param url
	 *  @param params
	 *  @param headers
	 *  @return
	 *  @throws IOException
	 */
	public HttpResult doPut(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
		// 创建http Put请求
		HttpPut httpPut = new HttpPut(url) ;
		httpPut.setConfig(HttpClientManager.getRequestConfig());
		// 构造头部信息
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpPut.setHeader(header.getKey(), header.getValue());
				//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				//httpPost.setHeader("Authorization", "Bearer "+token);
				//httpPost.setHeader("Accept", "application/json");
			}
		}
		
		if (params != null) {
			// 设置2个post参数，一个是scope、一个是q
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPut.setEntity(formEntity);
		}
		
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpPut);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	/**
	 *  doPutJson:(Put请求传输Json数据). 
	 *  @return_type:HttpResult
	 *  @author zhangtian 
	 *  @param url
	 *  @param json
	 *  @param headers
	 *  @return
	 *  @throws ClientProtocolException
	 *  @throws IOException
	 */
	public HttpResult doPutJson(String url, String json, Map<String, String> headers) throws ClientProtocolException, IOException {
		// 创建http POST请求
		HttpPut httpPut = new HttpPut(url);
		httpPut.setConfig(HttpClientManager.getRequestConfig());
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpPut.setHeader(header.getKey(), header.getValue());
			}
		}
		
		if (json != null) {
			// 构造一个form表单式的实体
			/**
			 * 这里是json数据的请求头，所以服务端需要是下面的json请求头，否则无法接收到json串数据
			 *  @Path("demo")
				@POST
				@Produces("application/json")
				public String demo(String json)
			 */
			StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			// 将请求实体设置到httpPost对象中
			httpPut.setEntity(stringEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpPut);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	// =========================== Delete请求  ===========================
	/**
	 *  doDelete:(删除数据). 
	 *  @return_type:String
	 *  @author zhangtian 
	 *  @param url
	 *  @param headers
	 *  @return
	 *  @throws ClientProtocolException
	 *  @throws IOException
	 */
	public String doDelete(String url, Map<String, String> headers) throws ClientProtocolException, IOException {
		// 创建http GET请求
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setConfig(HttpClientManager.getRequestConfig());
		// 构造头部信息
		if(headers != null) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				httpDelete.setHeader(header.getKey(), header.getValue());
			}
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = HttpClientManager.createCloseableHttpClient().execute(httpDelete);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	/**
	 *  doDelete:(携带参数删除数据). 
	 *  @return_type:String
	 *  @author zhangtian 
	 *  @param url
	 *  @param params
	 *  @param headers
	 *  @return
	 *  @throws ClientProtocolException
	 *  @throws IOException
	 *  @throws URISyntaxException
	 */
	public String doDelete(String url, Map<String, String> params, Map<String, String> headers)
			throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}
		return this.doDelete(uriBuilder.build().toString(), headers);
	}
}
