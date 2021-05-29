package it.nicolalopatriello.thesis.common.client;

import com.gfmi.net.HttpClient;
import com.gfmi.net.HttpClientResponse;
import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.common.exception.RefusedRequestException;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Log4j
public abstract class AbstractClient {

    protected static final HttpClient httpClient = HttpClient.create();

    protected <E> E put(String baseHostUrl, String path, Jsonizable body, Class<E> resultClass, Map<String, String> header) throws RefusedRequestException, NotFoundException, BadRequestException {
        String url = baseHostUrl + path;
        HttpClientResponse res;
        try {
            res = httpClient.sendPut(url, body.toJson(), header);
        } catch (Exception e) {
            throw new RefusedRequestException(e);
        }
        evaluateResponse(res);
        return Jsonizable.fromJson(res.getMessage(), resultClass);
    }

    protected <E> E post(String baseHostUrl, String path, Jsonizable body, Class<E> resultClass, Map<String, String> header) throws RefusedRequestException, NotFoundException, BadRequestException {
        String url = baseHostUrl + path;
        HttpClientResponse res;
        try {
            res = httpClient.sendPost(url, body.toJson(), header);
        } catch (Exception e) {
            throw new RefusedRequestException(e);

        }
        evaluateResponse(res);
        return Jsonizable.fromJson(res.getMessage(), resultClass);
    }

    protected <E> E get(String baseHostUrl, String path, Class<E> resultClass, Map<String, String> header) throws RefusedRequestException, NotFoundException, BadRequestException {
        String url = baseHostUrl + path;
        return get(url, resultClass, header);
    }

    protected <E> E get(String url, Class<E> resultClass, Map<String, String> header) throws RefusedRequestException, NotFoundException, BadRequestException {
        HttpClientResponse res;
        try {
            res = httpClient.sendGet(url, header);
        } catch (Exception e) {
            throw new RefusedRequestException(e);
        }
        evaluateResponse(res);
        return Jsonizable.fromJson(res.getMessage(), resultClass);
    }

    protected <E extends HttpRequestBase> E addHeader(E req, Map<String, String> header) {
        if (header != null) {
            for (Map.Entry<String, String> e : header.entrySet()) {
                req.addHeader(e.getKey(), e.getValue());
            }
        }
        return req;
    }

    protected org.apache.http.client.HttpClient createClient(String url) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RequestConfig requestConfig = RequestConfig.custom().build();
        if (url.startsWith("https")) {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(requestConfig).build();
        } else {
            return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        }
    }

    protected HttpResponse get(String baseHostUrl, String path, Map<String, String> header) throws RefusedRequestException {
        String url = baseHostUrl + path;
        try {
            org.apache.http.client.HttpClient client = this.createClient(url);
            HttpRequestBase httpRequest = this.addHeader(new HttpGet(url), header);
            return client.execute(httpRequest);
        } catch (Exception e) {
            throw new RefusedRequestException(e);
        }
    }

    protected void delete(String baseHostUrl, String path, Map<String, String> header) throws RefusedRequestException, NotFoundException, BadRequestException {
        String url = baseHostUrl + path;
        HttpClientResponse res;
        try {
            res = httpClient.sendDel(url, header);
        } catch (Exception e) {
            throw new RefusedRequestException(e);
        }
        evaluateResponse(res);
    }

    protected void evaluateResponse(HttpClientResponse res) throws NotFoundException, BadRequestException, RefusedRequestException {
        if (res.getCode() / 100 == 2)
            return;
        if (res.getCode() == 404)
            throw new NotFoundException();
        if (res.getCode() == 400)
            throw new BadRequestException();
        throw new RefusedRequestException(res);

    }
}
