package it.nicolalopatriello.thesis.runner.client;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.runner.watchers.impl.PythonWatcherArgs;
import lombok.extern.log4j.Log4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import it.nicolalopatriello.thesis.runner.exception.HttpRequestException;

import java.util.List;
import java.util.Optional;

@Log4j
public class ThesisCoreHttpClient {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();
    public Optional<RunnerJobResponse> findJob0() throws HttpRequestException {
        RunnerJobResponse w = new RunnerJobResponse();
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

    public Optional<RunnerJobResponse> findJob() throws HttpRequestException {
        HttpGet request = new HttpGet("http://localhost:8080/api/core/job/");
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return Optional.of(gson.fromJson(result, RunnerJobResponse.class));
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
