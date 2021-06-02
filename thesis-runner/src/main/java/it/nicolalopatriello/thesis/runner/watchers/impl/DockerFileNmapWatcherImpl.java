package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j
public class DockerFileNmapWatcherImpl implements Watcher<DockerFileNmapWatcherArgs> {
    public static final String DOCKERFILE = "Dockerfile";
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
            File req = new File(fileList[0].getAbsolutePath());
            try {
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
                long current = dateFormat.parse(versions.get("nmap-7.70.tar.bz2")).getTime();
                long latest = dateFormat.parse(versions.get(lastVersionName)).getTime();
                int days = (int) TimeUnit.MILLISECONDS.toDays(latest - current);
                System.err.println("last version " + lastVersionName);
                System.err.println("days: " + days);



                w.setMetrics();
                w.setSuccess(true);
            } catch (Exception e) {
                log.error("Dockerfile namp version extraction failed");
                w.setSuccess(false);
            }
        }
        return w;
    }

    @Override
    public DockerFileNmapWatcherArgs convert(Object args) {
        return null;
    }
}
