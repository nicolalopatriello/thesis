package it.nicolalopatriello.thesis.runner.watchers.impl;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.dto.Dependency;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class PythonWatcherImpl implements Watcher<PythonWatcherArgs> {

    public static final String REQUIREMENTS_TXT = "requirements.txt";

    @Override
    public WatcherResponse run(File folder, PythonWatcherArgs args) {
        System.err.println("[PYTHON] Folder: " + folder + " args: " + args);
        WatcherResponse w = new WatcherResponse();
        List<Dependency> deps = Lists.newLinkedList();
        File[] fileList = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals(REQUIREMENTS_TXT);
            }
        });
        if (fileList != null && fileList.length > 0) {
            //extract dep

            System.err.println(fileList[0].getAbsolutePath());
        }
        return w;
    }

    @Override
    public PythonWatcherArgs convert(Object args) {
        return null;
    }
}
