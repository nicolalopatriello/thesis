package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RunnerResponse {
    private String commitSha;
    private List<WatcherResponse> watchers;
}
