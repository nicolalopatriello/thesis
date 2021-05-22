package it.nicolalopatriello.thesis.worker.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import it.nicolalopatriello.thesis.worker.exception.HttpRequestException;
import java.util.Optional;

public class ThesisCoreHttpClient {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();

    public Optional<WorkerJobResponse> findJob() throws HttpRequestException {
        HttpGet request = new HttpGet("http://localhost:8080/api/core/job/");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return Optional.of(gson.fromJson(result, WorkerJobResponse.class));
            } else {
                throw new HttpRequestException("[Find Job] Null response entity");
            }
        } catch (Exception e) {
            throw new HttpRequestException("[Find Job] Response exception");
        }
    }
}
