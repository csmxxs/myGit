package com.ylms.liuliangbao.utlis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.apache.commons.io.IOUtils;
/**
 * <b>function:</b> HTTPUtil常用工具类 包含post 和get 方法
 * May 15, 2016 5:38:40 PM
 *
 * @author su_jian
 */
public class HttpUtil {
	/**
	 * 日志管理类
	 */
	//private static Logger logger = Logger.getLogger(HttpUtil.class);
	/**
	 * 超时
	 */
	@SuppressWarnings("unused")
	private static final int TIMEOUT = 1000 * 5;
	/**
	 * client
	 */
	private static final CloseableHttpClient HTTP_CLIENT;
	/**
	 * 默认编码
	 */
	public static final String CHARSET = "UTF-8";

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000000).build();
		HTTP_CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, CHARSET);
	}

	public static String doPost(String url, Map<String, String> params) {
		return doPost(url, params, CHARSET);
	}

	public static String doPost(String url, String params) {
		return doPost(url, params, CHARSET);
	}

	/**
	 * HTTP Get 获取内容
	 *
	 * @param url     请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 页面内容
	 */
	public static String doGet(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}

			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HTTP Post 获取内容
	 *
	 * @param url     请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 页面内容
	 */
	public static String doPost(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HTTP Post 获取内容 传入String类型的POSt方法
	 *
	 * @param url     请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 页面内容
	 */
	public static String doPost(String url, String params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null && params.length() > 0) {
				StringEntity stringEntity = new StringEntity(params, "UTF-8");
				stringEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(stringEntity);
			}
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * HTTP Post 获取内容
	 *
	 * @param url     请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 页面内容
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, String> requestHeaderMap, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					} else {
						pairs.add(new BasicNameValuePair(entry.getKey(), ""));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			for (Map.Entry<String, String> entry : requestHeaderMap.entrySet()) {
				String value = entry.getValue();
				if (value != null) {
					httpPost.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
				}
			}
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 创建一个HTTPS客户端
	 *
	 * @return
	 */
	public CloseableHttpClient createHttpsClient() {
		X509TrustManager x509mgr = new X509TrustManager() {
		
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}
		
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}
		
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		try {
			sslContext.init(null, new TrustManager[]{x509mgr}, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

	/**
	 * HTTP Post 获取内容
	 *
	 * @param url     请求的url地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 页面内容
	 */
	public static String doPostHttps(String url, Map<String, Object> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		HttpUtil t = new HttpUtil();
		CloseableHttpClient httpclient = t.createHttpsClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					String value = entry.getValue().toString();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			HttpResponse httpResp = httpclient.execute(httpPost);
			int statusCode = httpResp.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity entity = httpResp.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return null;
	}

	/**
	 * 描述：发送 http post 请求，参数以原生字符串进行提交
	 *
	 * @param url        请求url
	 * @param stringJson json字符串
	 * @param headers    请求头
	 * @param encode     字符编码
	 * @return
	 */
	public static String httpPostRaw(String url, String stringJson, Map<String, String> headers, String encode) {
		if (encode == null) {
			encode = "utf-8";
		}
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		// 设置header
		httpost.setHeader("Content-type", "application/json");
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		// 组织请求参数
		StringEntity stringEntity = new StringEntity(stringJson, encode);
		httpost.setEntity(stringEntity);
		String content = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 响应信息
			httpResponse = closeableHttpClient.execute(httpost);
			HttpEntity entity = httpResponse.getEntity();
			content = EntityUtils.toString(entity, encode);
		} catch (Exception e) {
			//logger.error("** send post request exception {} **", e);
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
				//logger.error("** close http connection exception {} **", e);
				e.printStackTrace();
			}
		}
		try {
			closeableHttpClient.close();
		} catch (IOException e) {
			//logger.error("** close http connection exception {} **", e);
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 描述：发送post请求, 请求参数是map
	 *
	 * @param url     请求地址
	 * @param param   请求参数
	 * @param charSet 编码
	 */
	@Deprecated
	public static String sendPost(String url, Map<?, ?> param, String charSet) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<?, ?> entry : param.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			sb.deleteCharAt(sb.length() - 1);

			// 发送请求参数
			out.print(sb);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally { // 使用finally块来关闭输出流、输入流
			//IOUtils.closeQuietly(out);
			//IOUtils.closeQuietly(in);
		}
		return result;
	}

	/**
	 * HTTP Get 获取内容
	 *
	 * @param url        请求的url地址 ?之前的地址
	 * @param params     请求的参数
	 * @param reuestMaps 请求的head信息
	 * @param charset    编码格式
	 * @return 页面内容
	 */
	public static String doGet(String url, Map<String, String> params, Map<String, String> reuestMaps, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);
			// http 设置请求cookies
			for (Map.Entry<String, String> entry : reuestMaps.entrySet()) {
				String value = entry.getValue();
				if (value != null) {
					httpGet.addHeader(new BasicHeader((String) entry.getKey(), (String) entry.getValue()));
				}
			}
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取图片验证码
	 *
	 * @param url     请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset 编码格式
	 * @return 生成的图片验证码base64编码字符串
	 */
	/*public static String doGetVCode(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}

			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String contentType = entity.getContentType().getValue();
			byte[] bytes = EntityUtils.toByteArray(entity);
			if (null != bytes && bytes.length > 0) {
				String base64Str = java.util.Base64.getEncoder().encodeToString(bytes);
				base64Str = "data:"+contentType+";base64," + base64Str;
				return base64Str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/


}
