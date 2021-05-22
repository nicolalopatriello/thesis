package it.nicolalopatriello.thesis.worker.watchers.impl;

import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.worker.watchers.Watcher;

import java.io.File;

public class PythonWatcherImpl implements Watcher<PythonWatcherArgs> {

    @Override
    public WatcherResponse run(File folder, PythonWatcherArgs args) {
        System.err.println("[PYTHON] Folder: "+folder +" args: "+args);
        return null;
    }

    @Override
    public PythonWatcherArgs convert(Object args) {
        return null;
    }
}
