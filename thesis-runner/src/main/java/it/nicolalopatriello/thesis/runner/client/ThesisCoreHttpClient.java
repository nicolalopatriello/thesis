package it.nicolalopatriello.thesis.runner.client;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.runner.exception.HttpRequestException;
import lombok.extern.log4j.Log4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.Optional;

@Log4j
public class ThesisCoreHttpClient {
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public Optional<RunnerJobResponse> findJob() throws HttpRequestException {
        HttpGet request = new HttpGet("http://localhost:8080/api/core/job/");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return Optional.of(Jsonizable.fromJson(result, RunnerJobResponse.class));
            } else {
                throw new HttpRequestException("[Find Job] Null response entity");
            }
        } catch (Exception e) {
            throw new HttpRequestException("[Find Job] Response exception");
        }
    }

    public void send(RunnerResponse runnerResponse) throws HttpRequestException {
        HttpEntity stringEntity = new StringEntity(Jsonizable.toJson(runnerResponse),ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost("http://localhost:8080/api/core/job/");
        postMethod.setEntity(stringEntity);
        try {
            httpClient.execute(postMethod);
        } catch (IOException e) {
            throw new HttpRequestException("[Find Job] Response exception");
        }
    }
}
