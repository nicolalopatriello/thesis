package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.dto.DependencyLight;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.Utility;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Log4j
public class PythonWatcherImpl implements Watcher<PythonWatcherArgs> {

    public static final String REQUIREMENTS_TXT = "requirements.txt";

    @Override
    public WatcherResponse run(File folder, PythonWatcherArgs args) {
        System.err.println("[PYTHON] Folder: " + folder + " args: " + args);
        WatcherResponse w = new WatcherResponse();
        File[] fileList = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals(REQUIREMENTS_TXT);
            }
        });
        if (fileList != null && fileList.length > 0) {
            File req = new File(fileList[0].getAbsolutePath());
            try {
                w.setDependencies(extract(req));
                w.setSuccess(true);
            } catch (Exception e) {
                log.error("Dependencies extraction failed");
                w.setSuccess(false);
            }
        }
        return w;
    }

    private List<DependencyLight> extract(File f) throws IOException {
        List<DependencyLight> list = Lists.newLinkedList();
        try (FileReader file = new FileReader(f); BufferedReader br = new BufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                Optional<DependencyLight> parsed = Utility.parsePythonDependency(line);
                parsed.ifPresent(list::add);
            }
        }
        return list;
    }

    @Override
    public PythonWatcherArgs convert(Object args) {
        return null;
    }
}
