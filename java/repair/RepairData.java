package repair;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RepairData {
	public static void main(String[] args) throws Exception {
		// 数据请求报文
		String path = "";
		File f = new File(path);
		List<String> list = Files.readLines(f, Charsets.UTF_8);
		// 请求地址
		String url = "";
		for (String json : list) {
			try {
				String resp = doPost(url, json);
				System.out.println(resp);
			} catch (Exception e) {
				System.out.println(json);
			}
		}
	}


	public static String doPost(String url, String params) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				return EntityUtils.toString(responseEntity);
			} else {
				System.out.println("请求返回:" + state + "(" + url + ")");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
