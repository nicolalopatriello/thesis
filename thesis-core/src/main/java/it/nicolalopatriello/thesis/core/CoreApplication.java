package it.nicolalopatriello.thesis.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.nicolalopatriello.thesis.common.spring.jpa.JpaSpecificationExecutorWithProjectionImpl;
import it.nicolalopatriello.thesis.core.dto.dependecy.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.dto.dependecy.PythonDependency;
import it.nicolalopatriello.thesis.core.service.RepositoryService;
import it.nicolalopatriello.thesis.core.utils.Utility;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@Log4j
@EnableScheduling
public class CoreApplication {
	public static void main(String[] args) throws GitAPIException {
		/*SpringApplication springApplication = new SpringApplication(CoreApplication.class);
		springApplication.run();*/

		final File localPath = new File("./TestRepo");
		Git.cloneRepository()
				.setURI("https://repository.v2.moon-cloud.eu/nlopatriello/probe-dependecy.git")
				.setDirectory(localPath)
				.setCredentialsProvider(new UsernamePasswordCredentialsProvider("nlopatriello", "intothewild_90"))
				.call();

	}

   /* public static void main(String[] args) throws GitLabApiException, IOException {


        GitLabApi gitLabApi = new GitLabApi("https://repository.v2.moon-cloud.eu/", "mUycgC2Vfis274emsJUD");

        InputStream t = gitLabApi.getRepositoryFileApi().getRawFile(86, "master", "requirements.txt");

        Stream<String> text = new BufferedReader(
                new InputStreamReader(t, StandardCharsets.UTF_8)).lines();

        String url = "https://raw.githubusercontent.com/pyupio/safety-db/master/data/insecure_full.json";

        FileUtils.copyURLToFile(
                new URL("https://raw.githubusercontent.com/pyupio/safety-db/master/data/insecure_full.json"),
                new File("/tmp/db.json"));

        BufferedReader br = new BufferedReader(new FileReader("/tmp/db.json"));
        System.err.println(br);

        br.lines().forEach(System.err::println);

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<DependencyVulnerability>>>(){}.getType();
        Map<String, List<DependencyVulnerability>> decoded = gson.fromJson(br, type);
        System.out.println(decoded.get("zwiki").get(0).toString());

       text.forEach(f -> {
            Optional<PythonDependency> opt = Utility.parsePythonDependency(f);
            if (opt.isPresent()) {
                System.err.println(opt.get().toString());
                DependencyVulnerability py = myMap.get(opt.get().getName());
                System.err.println(":..");
                System.err.println(py.toString());
            }
        });

    }*/
}
