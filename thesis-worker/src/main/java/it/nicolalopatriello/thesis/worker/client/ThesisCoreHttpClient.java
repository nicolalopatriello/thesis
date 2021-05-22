package it.nicolalopatriello.thesis.worker.client;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.worker.watchers.impl.PythonWatcherArgs;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import it.nicolalopatriello.thesis.worker.exception.HttpRequestException;

import java.util.List;
import java.util.Optional;

public class ThesisCoreHttpClient {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();
    public Optional<WorkerJobResponse> findJob() throws HttpRequestException {
        WorkerJobResponse w = new WorkerJobResponse();
        Recipe recipe = new Recipe();
        List<Recipe.Item> items = Lists.newLinkedList();
        Recipe.Item i = new Recipe.Item();
        i.setWatcherType(WatcherType.PYTHON_DEPENDENCY);
        i.setArgs(new PythonWatcherArgs());
        items.add(i);

        w.setRepositoryId(10L);
        w.setRepositoryUrl("https://github.com/apache/hbase.git");

        recipe.setItems(items);
        w.setRecipe(recipe);
        return Optional.of(w);
    }
    public Optional<WorkerJobResponse> findJob0() throws HttpRequestException {
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

    public void send(RunnerResponse response) {
    }
}
