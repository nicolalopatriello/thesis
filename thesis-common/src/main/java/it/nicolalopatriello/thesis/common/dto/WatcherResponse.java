package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WatcherResponse {
    private boolean success;
    private List<DependencyLight> dependencies;
    private List<Metric> metrics;

    public static WatcherResponse fail() {
        WatcherResponse w = new WatcherResponse();
        w.setSuccess(false);
        return w;
    }

}
