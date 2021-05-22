package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WatcherResponse {
    private boolean success;
    private List<Dependency> dependencies;
    private List<Metric> metrics;

    public static WatcherResponse fail() {
        WatcherResponse w = new WatcherResponse();
        w.setSuccess(false);
        return w;
    }


    public static class Dependency {
        private String name;
        private String version;
    }

    public static class Metric {
        private String key;
        private Object value;
    }

}
