import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import java.io.IOException;

public class WorkerApplication {
    private static final Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();

    public static void main(String[] args) throws InterruptedException, IOException {

        while (true) {
            System.err.println("aaaa");
            Thread.sleep(1000);

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet("http://localhost:8080/api/core/job");
                Object tfdsa = client.execute(request, reponse-> reponse.getEntity().getContent());
                System.err.println(tfdsa);
            }

        }
    }
}
