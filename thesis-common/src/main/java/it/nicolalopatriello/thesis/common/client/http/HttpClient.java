package it.nicolalopatriello.thesis.common.client.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClient {
    protected static final Logger log = Logger.getLogger(HttpClient.class);
    protected int sslPort;
    protected TrustStrategy acceptingTrustStrategy;
    protected Charset charset;
    private RequestConfig requestConfig;

    protected HttpClient(int sslPort) {
        this.acceptingTrustStrategy = (chain, authType) -> {
            return true;
        };
        this.charset = Charset.forName(System.getProperty("httpclient.charset", "UTF-8"));
        this.requestConfig = RequestConfig.custom().build();
        this.sslPort = sslPort;
    }

    protected HttpClient() {
        this(8443);
    }

    public HttpClient withRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public static HttpClient create(int sslPort) {
        return new HttpClient(sslPort);
    }

    public static HttpClient create() {
        return new HttpClient();
    }

    protected org.apache.http.client.HttpClient createClient(String url) throws Exception {
        if (url.startsWith("https")) {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial((KeyStore) null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(this.requestConfig).build();
        } else {
            return HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
        }
    }

    protected HttpClientResponse getHttpClientResponse(HttpResponse response) {
        String value = null;
        InputStreamReader in = null;
        BufferedReader br = null;
        if (response.getEntity() != null) {
            try {
                in = new InputStreamReader(response.getEntity().getContent(), this.charset);
                br = new BufferedReader(in);
                StringBuilder result = new StringBuilder();
                br.lines().forEach(result::append);
                value = result.toString();
            } catch (IOException var18) {
                log.error(var18.getMessage(), var18);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException var17) {
                        log.error(var17.getMessage(), var17);
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException var16) {
                        log.error(var16.getMessage(), var16);
                    }
                }

            }
        }

        int code = response.getStatusLine().getStatusCode();
        if (log.isDebugEnabled()) {
            log.debug("Response code=" + code + " value=" + value);
        }

        return new HttpClientResponse(code, value, response);
    }

    protected HttpClientResponse executeHttpRequest(String url, HttpRequestBase httpRequest) throws Exception {
        org.apache.http.client.HttpClient client = this.createClient(url);
        HttpResponse response = client.execute(httpRequest);
        if (log.isDebugEnabled()) {
            log.debug("Sending " + httpRequest.getMethod() + " request to URL : " + url);
            log.debug("Response Code : " + response.getStatusLine().getStatusCode());
        }

        return this.getHttpClientResponse(response);
    }

    public HttpClientResponse sendGet(String url) throws Exception {
        return this.sendGet(url, (Map) null);
    }

    public HttpClientResponse sendGet(String url, Map<String, String> header) throws Exception {
        HttpRequestBase get = this.addHeader(new HttpGet(url), header);
        return this.executeHttpRequest(url, get);
    }

    protected <E extends HttpRequestBase> E addHeader(E req, Map<String, String> header) {
        if (header == null) {
            return req;
        } else {
            Iterator var3 = header.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry) var3.next();
                req.addHeader((String) e.getKey(), (String) e.getValue());
            }

            return req;
        }
    }

    public HttpClientResponse sendPostForm(String url, Map<String, Object> bodyParams) throws Exception {
        return this.sendPostForm(url, bodyParams, (Map) null);
    }

    public HttpClientResponse sendPostForm(String url, Map<String, Object> bodyParams, Map<String, String> header) throws Exception {
        HttpPost request = (HttpPost) this.addHeader(new HttpPost(url), header);
        ArrayList<NameValuePair> postParameters = (ArrayList) bodyParams.entrySet().stream().map((kv) -> {
            return new BasicNameValuePair((String) kv.getKey(), String.valueOf(kv.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
        request.setEntity(new UrlEncodedFormEntity(postParameters));
        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendPost(String url, String bodyParamsJson) throws Exception {
        return this.sendPost(url, (String) bodyParamsJson, (Map) null);
    }

    public HttpClientResponse sendPost(String url, String bodyParamsJson, Map<String, String> header) throws Exception {
        HttpPost request = (HttpPost) this.addHeader(new HttpPost(url), header);
        request.setHeader("Content-Type", "application/json");
        if (bodyParamsJson != null) {
            request.setEntity(new ByteArrayEntity(bodyParamsJson.getBytes(StandardCharsets.UTF_8)));
        }

        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendPost(String url, byte[] data, Map<String, String> header) throws Exception {
        HttpPost request = (HttpPost) this.addHeader(new HttpPost(url), header);
        if (data != null) {
            request.setEntity(new ByteArrayEntity(data));
        }

        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendPost(String url, byte[] data) throws Exception {
        return this.sendPost(url, (byte[]) data, new HashMap());
    }

    public HttpClientResponse sendPost(String url) throws Exception {
        HttpPost request = new HttpPost(url);
        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendDel(String url, Map<String, String> header) throws Exception {
        return this.executeHttpRequest(url, this.addHeader(new HttpDelete(url), header));
    }

    public HttpClientResponse sendDel(String url, byte[] data, Map<String, String> header) throws Exception {
        DeleteWithBody request = (DeleteWithBody) this.addHeader(new DeleteWithBody(url), header);
        if (data != null) {
            request.setEntity(new ByteArrayEntity(data));
        }

        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendDel(String url, String json, Map<String, String> header) throws Exception {
        return this.sendDel(url, json.getBytes(StandardCharsets.UTF_8), header);
    }

    public HttpClientResponse sendDel(String url) throws Exception {
        return this.sendDel(url, (Map) null);
    }

    public HttpClientResponse sendPatch(String url, String json) throws Exception {
        return this.sendPatch(url, json, (Map) null);
    }

    public HttpClientResponse sendPatch(String url, String json, Map<String, String> header) throws Exception {
        HttpPatch request = (HttpPatch) this.addHeader(new HttpPatch(), header);
        request.setHeader("Content-Type", "application/json");
        if (json != null) {
            request.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));
        }

        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendPut(String url, String json) throws Exception {
        return this.sendPut(url, (String) json, (Map) null);
    }

    public HttpClientResponse sendPut(String url, byte[] data) throws Exception {
        return this.sendPut(url, (byte[]) data, new HashMap());
    }

    public HttpClientResponse sendPut(String url, byte[] data, Map<String, String> header) throws Exception {
        HttpPut request = (HttpPut) this.addHeader(new HttpPut(url), header);
        if (data != null) {
            request.setEntity(new ByteArrayEntity(data));
        }

        return this.executeHttpRequest(url, request);
    }

    public HttpClientResponse sendPut(String url, String json, Map<String, String> header) throws Exception {
        HttpPut request = (HttpPut) this.addHeader(new HttpPut(url), header);
        request.setHeader("Content-Type", "application/json");
        if (json != null) {
            request.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));
        }

        return this.executeHttpRequest(url, request);
    }
}