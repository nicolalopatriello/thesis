package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.runner.dto.NmapVersion;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j
public class DockerFileNmapWatcherImpl implements Watcher<DockerFileNmapWatcherArgs> {
    public static final String DOCKERFILE = "Dockerfile";
    private final static Pattern NMAP_VERSIONS_PATTERN = Pattern.compile("(?<VERSION>nmap-\\d{1,5}.\\d{1,5}.tar.bz2)");
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final String NMAMP_VERSIONS_SOURCE = "https://ftp.osuosl.org/pub/blfs/conglomeration/nmap/";
    private static String lastVersionName;


    @Override
    public WatcherResponse run(File folder, DockerFileNmapWatcherArgs args, RunnerJobResponse.RepositoryCredentials creds) {
        WatcherResponse w = new WatcherResponse();
        File[] fileList = folder.listFiles((dir, name) -> name.equals(DOCKERFILE));
        if (fileList != null && fileList.length > 0) {
            File dockerFile = new File(fileList[0].getAbsolutePath());
            try {
                Optional<String> currentVersion = extractVersion(dockerFile);
                if (currentVersion.isPresent()) {
                    Document document = Jsoup.connect(NMAMP_VERSIONS_SOURCE).get();
                    Element table = document.select("table").get(0);
                    Elements rows = table.select("tr");
                    List<NmapVersion> versions = Lists.newLinkedList();

                    for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                        Element row = rows.get(i);
                        Elements cols = row.select("td");
                        if (cols.size() > 0) {
                            if (cols.get(1).text() != null && cols.get(2).text() != null)
                                lastVersionName = cols.get(1).text();
                            versions.add(new NmapVersion(cols.get(1).text(), cols.get(2).text()));
                        }
                    }

                    List<Metric> metrics = Lists.newLinkedList();
                    NmapVersion latest = versions.get(versions.size() - 1);
                    Optional<NmapVersion> current = findVersion(versions, currentVersion.get());

                    NmapVersion curr = current.orElse(null);
                    int versionDist = curr != null ? latestVersionDistance(versions, curr) : null;
                    Metric metric = buildMetric(curr, latest, versionDist);
                    metrics.add(metric);
                    w.setMetrics(metrics);
                } else {
                    w.setMetrics(Collections.emptyList());
                }
                w.setDependencies(Collections.emptyList());
                w.setSuccess(true);
            } catch (Exception e) {
                log.error("Dockerfile namp version extraction failed");
                w.setSuccess(false);
            }
        }
        return w;
    }

    private Metric buildMetric(NmapVersion curr, NmapVersion latest, int versionDist) throws ParseException {
        if (curr == null)
            return new Metric(Metric.Severity.UNDEFINED, "Detected NMAP versions not found in Nmap repository", WatcherType.DOCKERFILE_NMAP);

        if (versionDist == 0)
            return new Metric(Metric.Severity.UNDEFINED, "Your NMAP version is latest. Great!", WatcherType.DOCKERFILE_NMAP);

        long currVersionDate = dateFormat.parse(curr.getDate()).getTime();
        long latestVersionDate = dateFormat.parse(latest.getDate()).getTime();
        int dayDiff = (int) TimeUnit.MILLISECONDS.toDays(latestVersionDate - currVersionDate);
        int ago = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - latestVersionDate);

        String msg = String.format("Detected NPM version is (%s) and is (%d) version distance from latest version (%s). " +
                        "%n Date of latest NMAP version is (%s). " +
                        "%n Two versions are (%d) days apart." +
                        "%n Last version of NMAP was (%d) days ago." +
                        "%n Please update.",
                curr.getName(), versionDist, latest.getName(), latest.getDate(), dayDiff, ago);

        if (versionDist >= 1 && versionDist < 3)
            return new Metric(Metric.Severity.LOW, msg, WatcherType.DOCKERFILE_NMAP);
        if (versionDist >= 3 && versionDist < 6)
            return new Metric(Metric.Severity.MEDIUM, msg, WatcherType.DOCKERFILE_NMAP);
        return new Metric(Metric.Severity.HIGH, msg, WatcherType.DOCKERFILE_NMAP);

    }

    private int latestVersionDistance(List<NmapVersion> versions, NmapVersion nmapVersion) {
        int latestIdx = versions.size() - 1;
        int currentVersionIdx = versions.indexOf(nmapVersion);
        return latestIdx - currentVersionIdx;
    }

    private Optional<NmapVersion> findVersion(List<NmapVersion> versions, String v) {
        List<NmapVersion> vers = versions.stream().filter(version -> version.getName().equals(v)).collect(Collectors.toList());
        if (vers.size() > 0)
            return Optional.of(vers.get(0));
        return Optional.empty();
    }

    private Optional<String> extractVersion(File dockerFile) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(dockerFile));
        final StringBuilder contents = new StringBuilder();
        while (reader.ready()) {
            contents.append(reader.readLine());
        }
        reader.close();
        Matcher matcher = NMAP_VERSIONS_PATTERN.matcher(contents.toString());
        if (matcher.find()) {
            String version = matcher.group("VERSION");
            if (version != null)
                return Optional.of(version);
        }
        return Optional.empty();
    }

    @Override
    public DockerFileNmapWatcherArgs convert(Object args) {
        return null;
    }
}
