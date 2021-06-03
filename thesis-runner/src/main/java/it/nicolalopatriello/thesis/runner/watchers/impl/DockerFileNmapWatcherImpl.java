package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
public class DockerFileNmapWatcherImpl implements Watcher<DockerFileNmapWatcherArgs> {
    public static final String DOCKERFILE = "Dockerfile";
    private final static Pattern NMAP_VERSIONS_PATTERN = Pattern.compile("(?<VERSION>nmap-\\d{1,5}.\\d{1,5}.tar.bz2)");
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final String NMAMP_VERSIONS_SOURCE = "https://ftp.osuosl.org/pub/blfs/conglomeration/nmap/";
    private static String lastVersionName;


    @Override
    public WatcherResponse run(File folder, DockerFileNmapWatcherArgs args) {
        WatcherResponse w = new WatcherResponse();
        File[] fileList = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals(DOCKERFILE);
            }
        });
        if (fileList != null && fileList.length > 0) {
            File dockerFile = new File(fileList[0].getAbsolutePath());
            try {
                Optional<String> currentVersion = extractVersion(dockerFile);
                if (currentVersion.isPresent()) {
                    Document document = Jsoup.connect(NMAMP_VERSIONS_SOURCE).get();
                    Element table = document.select("table").get(0); //select the first table.
                    Elements rows = table.select("tr");
                    Map<String, String> versions = Maps.newHashMap();
                    for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                        Element row = rows.get(i);
                        Elements cols = row.select("td");
                        if (cols.size() > 0) {
                            if (cols.get(1).text() != null && cols.get(2).text() != null)
                                lastVersionName = cols.get(1).text();
                            versions.put(cols.get(1).text(), cols.get(2).text());
                        }
                    }
                    if (!lastVersionName.equals(currentVersion.get())) {
                        long current = dateFormat.parse(versions.get(currentVersion.get())).getTime();
                        long latest = dateFormat.parse(versions.get(lastVersionName)).getTime();
                        int days = (int) TimeUnit.MILLISECONDS.toDays(latest - current);
                        List<Metric> metrics = Lists.newLinkedList();
                        Metric metric = buildMetric(days, lastVersionName, currentVersion.get());
                        metrics.add(metric);
                        w.setMetrics(metrics);
                    } else {
                        w.setMetrics(Collections.emptyList());
                    }
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

    private Metric buildMetric(int days, String lastVersionName, String detectedVersion) {
        Metric m = new Metric();
        if (days < 8)
            m.setSeverity(Metric.Severity.LOW);
        if (days > 8 && days < 30)
            m.setSeverity(Metric.Severity.MEDIUM);
        if (days > 30)
            m.setSeverity(Metric.Severity.HIGH);
        m.setDescription(String.format("Detected NPM version is (%s) but a new version (%s) is available for %d days. Please update.",
                detectedVersion, lastVersionName, days));
        return m;
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
