package codes.util;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HanXiong on 2017/1/11.
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static final Map<String, String> HEADER_CONTENT_TYPE_JSON = ImmutableMap.of("Content-Type", "application/json");

    public static class RetryWhenIOException extends DefaultHttpRequestRetryHandler {

        // InterruptedIOException 一般是超时
        // ConnectException 一般是连接不上
        public RetryWhenIOException(int retryTimes) {
            super(retryTimes, true, Arrays.asList(
                    UnknownHostException.class,
                    SSLException.class
//                    InterruptedIOException.class,
//                    ConnectException.class,
            ));
        }
    }

    public static class HttpRemoteException extends RemoteException {
        private int statusCode = 0;
        private String url;

        public HttpRemoteException(String url, int statusCode, String m) {
            super(m);
            this.url = url;
            this.statusCode = statusCode;
        }

        public HttpRemoteException(String url, int statusCode, String m, Throwable t) {
            super(m, t);
            this.url = url;
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String getMessage() {
            return "url:" + url + ", statusCode:" + statusCode + ", msg:" + super.getMessage();
        }
    }

    private static Statistics statistics = new Statistics();

    public static String get(HttpClient c, String url, int connTimeout, int socketTimeout) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "GET", null, null, null, null);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String get(HttpClient c, String url, int connTimeout, int socketTimeout, Map<String, String> heads) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "GET", heads, null, null, null);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String get(HttpClient c, String urlTemplate, Map<String, Object> urlPathParam, int connTimeout, int socketTimeout, Map<String, String> heads) throws HttpRemoteException {
        String url = urlFromTemplate(urlTemplate, urlPathParam);

        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "GET", null, null, null, null);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(urlTemplate), s, e);
        }
    }

    public static String post(HttpClient c, String url, int connTimeout, int socketTimeout, String content) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "POST", null, content, null, Charsets.UTF_8);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String post(HttpClient c, String url, int connTimeout, int socketTimeout, Map<String, String> requestForm, Map<String, String> heads) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "POST", heads, null, requestForm, Charsets.UTF_8);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String post(HttpClient c, String url, int connTimeout, int socketTimeout, String content, Map<String, String> heads) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "POST", heads, content, null, Charsets.UTF_8);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String post(HttpClient c, String url, int connTimeout, int socketTimeout, byte[] content, Map<String, String> heads) throws HttpRemoteException {
        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "POST", heads, content, null, Charsets.UTF_8);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(url), s, e);
        }
    }

    public static String post(HttpClient c, String urlTemplate, Map<String, Object> urlPathParam, int connTimeout, int socketTimeout, String content, Map<String, String> heads) throws HttpRemoteException {
        String url = urlFromTemplate(urlTemplate, urlPathParam);

        long s = now();
        Throwable e = null;
        try {
            return innerHttpCall(c, url, connTimeout, socketTimeout, "POST", heads, content, null, Charsets.UTF_8);
        } catch (Throwable t) {
            e = t;
            throw t;
        } finally {
            statistics.statistics(toUri(urlTemplate), s, e);
        }
    }

    private static String innerHttpCall(HttpClient c, String url, int connTimeout, int socketTimeout, String method, Map<String, String> heads, Object requestBody, Map<String, String> requestForm,
                                        Charset charset) throws HttpRemoteException {

        HttpEntity entity = null;
        try {
            HttpRequestBase hr = null;

            if ("GET".equals(method)) {
                hr = new HttpGet(url);
            } else if ("POST".equals(method)) {
                hr = new HttpPost(url);

                if (requestBody != null) {
                    if (requestBody instanceof String) {
                        ((HttpPost) hr).setEntity(new StringEntity((String) requestBody, charset));
                    } else if (requestBody instanceof byte[]) {
                        ((HttpPost) hr).setEntity(new ByteArrayEntity((byte[]) requestBody));
                    }
                }
                if (requestForm != null) {
                    List<NameValuePair> params = new ArrayList<NameValuePair>(requestForm.size());
                    for (Map.Entry<String, String> e : requestForm.entrySet()) {
                        params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
                    }
                    ((HttpPost) hr).setEntity(new UrlEncodedFormEntity(params, charset));
                }
            }

            if (heads != null) {
                for (Map.Entry<String, String> e : heads.entrySet()) {
                    hr.addHeader(e.getKey(), e.getValue());
                }
            }

            hr.setConfig(requestConfigBuilder(hr).setSocketTimeout(socketTimeout).setConnectTimeout(connTimeout).build());

            HttpResponse r = c.execute(hr);
            entity = r.getEntity();

            int statusCode = r.getStatusLine().getStatusCode();
            String returnContent = EntityUtils.toString(entity);
            // 2xx
            if (statusCode / 100 == 2) {
                return returnContent;
            } else {
                throw new HttpRemoteException(url, statusCode, returnContent);
            }
        } catch (HttpRemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpRemoteException(url, 0, e.getMessage(), e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    private static RequestConfig.Builder requestConfigBuilder(HttpRequestBase h) {
        if (h.getConfig() == null) {
            return RequestConfig.custom();
        } else {
            return RequestConfig.copy(h.getConfig());
        }
    }

    private static String toUri(String url) {
        int p = url.indexOf("?");
        if (p >= 0) {
            return url.substring(0, p);
        }
        return url;
    }

    private static String urlFromTemplate(String urlTemplate, Map<String, Object> p) {
        if (p == null) {
            return urlTemplate;
        }
        String url = urlTemplate;
        for (Map.Entry<String, Object> e : p.entrySet()) {
            url = url.replace("{" + e.getKey() + "}", String.valueOf(e.getValue())) ;
        }
        return url;
    }

    public static List<List<String>> statisticsView() {
        return statistics.statisticsView();
    }


    private static class Statistics {

        private ConcurrentHashMap<String, Statistic> statistics = new ConcurrentHashMap<>();

        boolean onOffLogCostTime = false;

        boolean onOffStatistics = false;

        void statistics(String uri, long startTime, Throwable t) {
            long cost = now() - startTime;

            if (onOffLogCostTime) {
                logger.info("uri: {}, cost: {}", uri, cost);
            }

            if (onOffStatistics) {
                doStatistics(uri, cost, t);
            }
        }

        synchronized void doStatistics(String uri, long cost, Throwable t) {
            Statistic st = statistics.get(uri);

            if (st == null) {
                st = new Statistic();
                statistics.put(uri, st);

                logger.debug("new uri: {}", uri);
            }

            st.totalCost += cost;
            st.totalTimes++;
            if (t != null) {
                st.failTimes++;
            }
            st.maxCost = Math.max(st.maxCost, cost);
        }

        public List<List<String>> statisticsView() {
            List<List<String>> list = new ArrayList<>(statistics.size());

            for (Map.Entry<String, Statistic> e : statistics.entrySet()) {
                Statistic s = e.getValue();
                List<String> l = new ArrayList<>();

                l.add(e.getKey());
                l.add("" + s.totalTimes);
                l.add("" + s.failTimes);
                l.add("" + s.totalCost);
                l.add("" + s.maxCost);
                l.add("" + s.totalCost / s.totalTimes);

                list.add(l);
            }
            return list;
        }
    }

    private static class Statistic {

        long totalCost;

        long totalTimes;

        long failTimes;

        long maxCost;
    }

    private static long now() {
        return System.currentTimeMillis();
    }

}
