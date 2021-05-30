package it.nicolalopatriello.thesis.runner.client;

import com.gfmi.net.HttpClient;
import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.client.AbstractClient;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.runner.RunnerProperties;
import it.nicolalopatriello.thesis.runner.exception.HttpRequestException;

import java.util.Map;
import java.util.Optional;

import static it.nicolalopatriello.thesis.common.utils.ThesisConstant.RUNNER_SECRET_KEY;

public class CoreHttpClient extends AbstractClient {
    protected HttpClient httpClient;

    private final String baseUrl = "http://localhost:8080/api/core";


    public CoreHttpClient() {
        this.httpClient = HttpClient.create();
    }

    protected CoreHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public Optional<RunnerJobResponse> findJob() throws HttpRequestException {
        try {
            RunnerJobResponse r = get(baseUrl, "/job/", RunnerJobResponse.class, header());
            if (r != null)
                return Optional.of(r);
            return Optional.empty();
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        }
    }

    public void send(RunnerResponse r) throws HttpRequestException {
        try {
            post(baseUrl, "/job/", r, Object.class, header());
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        }
    }

    Map<String, String> header() {
        Map<String, String> s = Maps.newHashMap();
        s.put(RUNNER_SECRET_KEY, RunnerProperties.secret());
        return s;
    }

}
