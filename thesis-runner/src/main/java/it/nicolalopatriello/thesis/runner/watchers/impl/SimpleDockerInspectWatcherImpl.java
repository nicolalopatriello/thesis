package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.dto.DependencyLight;
import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.utils.DataEncryptor;
import it.nicolalopatriello.thesis.common.utils.ThesisConstant;
import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.runner.service.DockerInspectServiceImpl;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.List;

@Log4j
public class SimpleDockerInspectWatcherImpl implements Watcher<SimpleDockerInspectWatcherArgs> {


    @Override
    public WatcherResponse run(File folder, SimpleDockerInspectWatcherArgs args, RunnerJobResponse.RepositoryCredentials creds) {
        log.debug("Args: " + args.toString());
        System.err.println("èèèèèèèèèèèèèèèèèèèèèèèèèèèè " + creds.getRepositoryPassword());
        WatcherResponse r = new WatcherResponse();
        DockerInspectServiceImpl d = new DockerInspectServiceImpl();
        String inspectResponse = d.inspect(args.getServerAddress(), args.getDockerImage(), args.getDockerRunCmd(), creds);
        List<Metric> metrics = Lists.newLinkedList();
        Metric m = buildMetric(inspectResponse);
        metrics.add(m);
        r.setMetrics(metrics);
        r.setSuccess(true);
        return r;
    }

    private Metric buildMetric(String inspectResponse) {
        Metric m = new Metric();
        m.setWatcherType(WatcherType.SIMPLE_DOCKER_INSPECT);
        m.setDescription(inspectResponse);
        return new Metric(Metric.Severity.UNDEFINED, inspectResponse, WatcherType.SIMPLE_DOCKER_INSPECT);
    }

    @Override
    public SimpleDockerInspectWatcherArgs convert(Object args) {
        return Jsonizable.fromJson(Jsonizable.toJson(args), SimpleDockerInspectWatcherArgs.class);
    }
}
